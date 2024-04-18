package com.example.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.*;
import io.reactivex.Flowable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class ChatAIUtils {
    private static final ClientV4 client = new ClientV4.Builder("b47dc829f0f90f96d18d5fafce419c73.OUGQFO5R2nSY1OMh").build();
    private static final String requestIdTemplate = "mycompany-%d";
    private static final ObjectMapper mapper = defaultObjectMapper();

    public SseEmitter processStringAsStream(String input) {
        SseEmitter emitter = new SseEmitter();

        new Thread(() -> {
            try {
                ModelApiResponse response = processString(input);
                if (response.isSuccess()) {
                    AtomicBoolean isFirst = new AtomicBoolean(true);
                    mapStreamToAccumulator(response.getFlowable())
                            .doOnNext(accumulator -> {
                                try {
                                    if (isFirst.getAndSet(false)) {
                                        emitter.send("贝贝: ");
                                    }
                                    if (accumulator.getDelta() != null && accumulator.getDelta().getTool_calls() != null) {
                                        String jsonString = mapper.writeValueAsString(accumulator.getDelta().getTool_calls());
                                        emitter.send("工具: " + jsonString);
                                    }
                                    if (accumulator.getDelta() != null && accumulator.getDelta().getContent() != null) {
                                        emitter.send(accumulator.getDelta().getContent());
                                    }
                                } catch (IOException e) {
                                    emitter.completeWithError(e);
                                }
                            })
                            .doOnComplete(() -> {
                                try {
                                    emitter.send("\n");
                                    emitter.complete();
                                } catch (IOException e) {
                                    emitter.completeWithError(e);
                                }
                            })
                            .subscribe();
                }
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        }).start();

        return emitter;
    }


    public static Flowable<ChatMessageAccumulator> mapStreamToAccumulator(Flowable<ModelData> flowable) {
        return flowable.map(chunk -> {
            return new ChatMessageAccumulator(chunk.getChoices().get(0).getDelta(), null, chunk.getChoices().get(0), chunk.getUsage(), chunk.getCreated(), chunk.getId());
        });
    }


    public static ObjectMapper defaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.addMixIn(ChatFunction.class, ChatFunctionMixIn.class);
        mapper.addMixIn(ChatCompletionRequest.class, ChatCompletionRequestMixIn.class);
        mapper.addMixIn(ChatFunctionCall.class, ChatFunctionCallMixIn.class);
        return mapper;
    }

    private ModelApiResponse processString(String input) {
        // 创建一个聊天消息列表
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), input);
        messages.add(chatMessage);
        // 创建一个聊天完成请求对象
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(Boolean.TRUE)
                .messages(messages)
                .requestId(requestId)
                .build();

        // 调用模型API
        ModelApiResponse sseModelApiResp = client.invokeModelApi(chatCompletionRequest);
        return sseModelApiResp;
    }
}
