package com.example.listener;


import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "warn")
public class WarnQueueListener {

    @Resource
    JavaMailSender sender;

    @Value("${spring.mail.username}")
    String username;

    /**
     * 处理告警信息
     * 返回的Map是 email 和 data => 告警信息
     *
     * @param data
     */
    @RabbitHandler
    public void warnProcess(Map<String, Object> data) {
        System.out.println("WarnQueueListener: " + data);
        String email = data.get("email").toString();
        String id = data.get("id").toString();
        SimpleMailMessage message = createMessage("您的监控节点发生异常",
                "您好，您的监控节点:" + id + ",发生异常，异常信息: " + data.get("data"),
                email);
        sender.send(message);
    }

    private SimpleMailMessage createMessage(String title, String content, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(title);
        message.setText(content);
        message.setTo(email);
        message.setFrom(username);
        return message;
    }
}
