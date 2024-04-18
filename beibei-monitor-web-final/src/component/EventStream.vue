<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <span>SSE Client</span>
      </div>
    </template>
    <div v-for="(event, index) in events" :key="index" class="text item">
      Received: {{ event }}
    </div>
  </el-card>
</template>

<script>
import { ref, onMounted, onUnmounted } from 'vue';

export default {
  name: 'EventStream',
  setup() {
    const events = ref([]);

    let eventSource = null;

    onMounted(() => {
      eventSource = new EventSource('http://localhost:8080/chat/stream?input=hello');
      eventSource.onmessage = (event) => {
        console.info('EventSource received:', event.data)
        events.value.push(event.data);
      };
      eventSource.onerror = (error) => {
        console.error('EventSource failed:', error);
        eventSource.close();
        // 处理错误，例如显示错误消息或尝试重新连接
      };
    });

    onUnmounted(() => {
      if (eventSource) {
        eventSource.close();
      }
    });

    return {
      events,
    };
  },
};
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}
</style>
