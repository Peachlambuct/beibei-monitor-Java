<script setup>
import {osNameToIcon} from "@/tools";
import {onUnmounted, ref} from "vue";
import { get } from "@/net";
import {useStore} from "@/store";
import router from "@/router";
import axios from "axios";

const store = useStore()
const simpleList = ref([])

const warn = ref([])
get('/api/warn/yesterday', data => {
  warn.value = data
})

const subTaskList = ref([])
get('/api/task/getAllSubtask', data => {
  subTaskList.value = data
})


const localhostRuntimeData = ref()
const getLocalhostRuntimeData = () => {
  get('/api/monitor/getCurrentClientDetails', data => {
    localhostRuntimeData.value = data
  })
}

let intervalId = null;

const onlineCount = ref();
const fetchData = () => {
  getLocalhostRuntimeData();
  if (store.isAdmin) {
    get('/api/monitor/list', list => {
      simpleList.value = list;
      onlineCount.value = simpleList.value.filter(item => item.online === true).length;
    });
  }
};

fetchData();
intervalId = setInterval(fetchData, 10000);

onUnmounted(() => {
  if (intervalId) {
    clearInterval(intervalId);
  }
});

const newMessage = ref('');
const messages = ref([]);


let currentAIResponse = ref(null);

const sendMessage = () => {
  messages.value.push({
    id: messages.value.length,
    text: newMessage.value,
    sender: 'user'
  });
  currentAIResponse = ref({
    id: messages.value.length,
    text: '',
    sender: 'ai'
  });
  messages.value.push(currentAIResponse.value);
  getAIResponse(newMessage.value);
  newMessage.value = '';
};

function formatDate(value) {
  if (value) {
    let date = new Date(value);
    return date.getFullYear() + '-' + (date.getMonth() + 1).toString().padStart(2, '0') + '-' + date.getDate().toString().padStart(2, '0');
  }
}
const url = axios.defaults.baseURL
const getAIResponse = (input) => {
  const eventSource = new EventSource(`${url}/chat/stream?input=${input}`);

  eventSource.onmessage = (event) => {
    currentAIResponse.value.text += event.data;
  };

  eventSource.onerror = (error) => {
    console.error("Fetch failed:", error);
    eventSource.close();
    newMessage.value = ''
  };
};
</script>

<template>
  <div style="margin: 0 50px">
    <div style="font-size: 22px;font-weight: bold">
      <el-icon style="height: 100%">
        <svg t="1713073039215" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
             p-id="20220" width="200" height="200">
          <path
              d="M788.68 675.2H255.34c-6.54 0-13.09 0.02-19.63 0-3.37-0.01-6.72-0.22-10.07-0.65 2.22 0.3 4.43 0.6 6.65 0.89a75.002 75.002 0 0 1-18.12-4.95l5.97 2.52a75.898 75.898 0 0 1-15.91-9.24c1.69 1.3 3.37 2.61 5.06 3.91a76.56 76.56 0 0 1-13.58-13.58c1.3 1.69 2.61 3.37 3.91 5.06a75.898 75.898 0 0 1-9.24-15.91l2.52 5.97a74.878 74.878 0 0 1-4.95-18.12c0.3 2.22 0.6 4.43 0.89 6.65-0.75-5.79-0.65-11.56-0.65-17.38v-24.94-85.9V404.4v-90.15c0-13.65-0.07-27.31 0-40.96 0.02-3.29 0.23-6.56 0.65-9.82-0.3 2.22-0.6 4.43-0.89 6.65 0.89-6.24 2.54-12.29 4.95-18.12l-2.52 5.97a75.898 75.898 0 0 1 9.24-15.91c-1.3 1.69-2.61 3.37-3.91 5.06a76.56 76.56 0 0 1 13.58-13.58c-1.69 1.3-3.37 2.61-5.06 3.91a75.898 75.898 0 0 1 15.91-9.24l-5.97 2.52a74.878 74.878 0 0 1 18.12-4.95c-2.22 0.3-4.43 0.6-6.65 0.89 7.43-0.96 15.04-0.65 22.52-0.65h520.2c6.63 0 13.26-0.02 19.9 0 3.38 0.01 6.74 0.22 10.09 0.65-2.22-0.3-4.43-0.6-6.65-0.89 6.24 0.89 12.29 2.54 18.12 4.95l-5.97-2.52a75.898 75.898 0 0 1 15.91 9.24c-1.69-1.3-3.37-2.61-5.06-3.91a76.56 76.56 0 0 1 13.58 13.58c-1.3-1.69-2.61-3.37-3.91-5.06a75.898 75.898 0 0 1 9.24 15.91l-2.52-5.97a74.878 74.878 0 0 1 4.95 18.12c-0.3-2.22-0.6-4.43-0.89-6.65 0.75 5.79 0.65 11.56 0.65 17.38v306.12c0 13.65 0.07 27.31 0 40.96-0.02 3.29-0.23 6.56-0.65 9.82 0.3-2.22 0.6-4.43 0.89-6.65a75.002 75.002 0 0 1-4.95 18.12l2.52-5.97a75.898 75.898 0 0 1-9.24 15.91c1.3-1.69 2.61-3.37 3.91-5.06a76.56 76.56 0 0 1-13.58 13.58c1.69-1.3 3.37-2.61 5.06-3.91a75.898 75.898 0 0 1-15.91 9.24l5.97-2.52a74.878 74.878 0 0 1-18.12 4.95c2.22-0.3 4.43-0.6 6.65-0.89-3.21 0.42-6.43 0.63-9.67 0.65-13.07 0.09-25.61 11.44-25 25 0.6 13.47 10.99 25.09 25 25 43.57-0.29 83.68-30.66 94.16-73.34 3.03-12.34 2.96-24.48 2.96-36.97v-45.17-65.51-74.46-71.12-55.54-28.5c0-10.44-1.31-21.17-4.68-31.09-7.2-21.23-20.68-38.69-39.1-51.38-16.91-11.64-37.19-16.1-57.42-16.1H238.4c-1.68 0-3.36-0.02-5.04 0.03-23.03 0.62-43.95 9.28-61.57 23.86-22.28 18.44-33.59 47.13-33.59 75.6v341.58c0 14.19 0.24 28.38 5.14 41.96 7.64 21.16 21.28 38.77 40.17 51.08 15.23 9.93 33.79 14.98 51.89 15.07 4.89 0.03 9.78 0 14.67 0h538.62c13.08 0 25.6-11.5 25-25-0.61-13.54-10.99-25-25.01-25z"
              fill="#231815" p-id="20221"></path>
          <path
              d="M295.06 334.96V583.08c0 3.35-0.01 6.71 0 10.06v0.44c-0.13 3.44 0.59 6.65 2.15 9.63 1.01 3.1 2.73 5.78 5.17 8.05 2.26 2.44 4.95 4.16 8.05 5.17 2.98 1.57 6.19 2.28 9.63 2.15 2.22-0.3 4.43-0.6 6.65-0.89 4.24-1.19 7.91-3.33 11.03-6.43 4.49-4.89 7.32-10.9 7.32-17.68v-6.96-18.78-27.85-33.98-37.08-37.69-34.9-29.6-21.28c0-3.35 0.01-6.71 0-10.06v-0.44c0.13-3.44-0.59-6.65-2.15-9.63-1.01-3.1-2.73-5.78-5.17-8.05-2.26-2.44-4.95-4.16-8.05-5.17-2.98-1.57-6.19-2.28-9.63-2.15-2.22 0.3-4.43 0.6-6.65 0.89-4.24 1.19-7.91 3.33-11.03 6.43-4.49 4.89-7.32 10.9-7.32 17.68zM425.55 487.89l-0.18 10.52c-0.15 8.42-0.29 16.83-0.44 25.25-0.18 10.18-0.35 20.36-0.53 30.55-0.15 8.8-0.3 17.6-0.46 26.39-0.07 4.26-0.19 8.53-0.22 12.79v0.19c-0.13 3.44 0.59 6.65 2.15 9.63 1.01 3.1 2.73 5.78 5.17 8.05 2.26 2.44 4.95 4.16 8.05 5.17 2.98 1.57 6.19 2.28 9.63 2.15 2.22-0.3 4.43-0.6 6.65-0.89 4.24-1.19 7.91-3.33 11.03-6.43 1.3-1.69 2.61-3.37 3.91-5.06 2.26-3.9 3.4-8.11 3.41-12.62l0.18-10.52c0.15-8.42 0.29-16.83 0.44-25.25 0.18-10.18 0.35-20.36 0.53-30.55 0.15-8.8 0.3-17.6 0.46-26.39 0.07-4.26 0.19-8.53 0.22-12.79v-0.19c0.13-3.44-0.59-6.65-2.15-9.63-1.01-3.1-2.73-5.78-5.17-8.05-2.26-2.44-4.95-4.16-8.05-5.17-2.98-1.57-6.19-2.28-9.63-2.15-2.22 0.3-4.43 0.6-6.65 0.89-4.24 1.19-7.91 3.33-11.03 6.43-1.3 1.69-2.61 3.37-3.91 5.06-2.25 3.9-3.39 8.11-3.41 12.62zM551.11 334.96V583.08c0 3.35-0.01 6.71 0 10.06v0.44c-0.13 3.44 0.59 6.65 2.15 9.63 1.01 3.1 2.73 5.78 5.17 8.05 2.26 2.44 4.95 4.16 8.05 5.17 2.98 1.57 6.19 2.28 9.63 2.15 2.22-0.3 4.43-0.6 6.65-0.89 4.24-1.19 7.91-3.33 11.03-6.43 4.49-4.89 7.32-10.9 7.32-17.68v-6.96-18.78-27.85-33.98-37.08-37.69-34.9-29.6-21.28c0-3.35 0.01-6.71 0-10.06v-0.44c0.13-3.44-0.59-6.65-2.15-9.63-1.01-3.1-2.73-5.78-5.17-8.05-2.26-2.44-4.95-4.16-8.05-5.17-2.98-1.57-6.19-2.28-9.63-2.15-2.22 0.3-4.43 0.6-6.65 0.89-4.24 1.19-7.91 3.33-11.03 6.43-4.49 4.89-7.32 10.9-7.32 17.68zM676.47 493.62v87.65c0 4.04-0.03 8.08 0 12.13v0.17c-0.13 3.44 0.59 6.65 2.15 9.63 1.01 3.1 2.73 5.78 5.17 8.05 2.26 2.44 4.95 4.16 8.05 5.17 2.98 1.57 6.19 2.28 9.63 2.15 2.22-0.3 4.43-0.6 6.65-0.89 4.24-1.19 7.91-3.33 11.03-6.43 4.49-4.89 7.32-10.9 7.32-17.68v-9.92-23.83-28.97-24.93c0-4.04 0.03-8.08 0-12.13v-0.17c0.13-3.44-0.59-6.65-2.15-9.63-1.01-3.1-2.73-5.78-5.17-8.05-2.26-2.44-4.95-4.16-8.05-5.17-2.98-1.57-6.19-2.28-9.63-2.15-2.22 0.3-4.43 0.6-6.65 0.89-4.24 1.19-7.91 3.33-11.03 6.43-4.49 4.9-7.32 10.91-7.32 17.68zM274.27 833.49h456.15c6.16 0 12.33 0.04 18.49 0h0.81c6.41 0 13.14-2.79 17.68-7.32 4.34-4.34 7.6-11.45 7.32-17.68-0.29-6.47-2.41-13.17-7.32-17.68-4.89-4.49-10.9-7.32-17.68-7.32H293.57c-6.16 0-12.33-0.04-18.49 0h-0.81c-6.41 0-13.14 2.79-17.68 7.32-4.34 4.34-7.6 11.45-7.32 17.68 0.29 6.47 2.41 13.17 7.32 17.68 4.9 4.49 10.91 7.32 17.68 7.32z"
              fill="#231815" p-id="20222"></path>
        </svg>
      </el-icon>
      服务器总览
    </div>
    <div style="font-size: 15px;color: grey">查看本系统管理的全部服务器的基本信息</div>

    <el-divider style="margin: 10px 0"/>

    <div style="width: 100%;height: 400px;display: flex">
      <div style="flex: 5;">
        <div class="server-info-card">
          <span class="title">此服务器运行指标:</span>
          <div style="margin: 25px" v-if="localhostRuntimeData">
            <div class="metrics">
              <div class="metric">
                <span>CPU占用量: </span>
                <span class="value">{{ localhostRuntimeData.cpuUsage.toFixed(2) }}%</span>
              </div>
              <div class="metric">
                <span>内存占用量: </span>
                <span class="value">{{ localhostRuntimeData.memoryUsage.toFixed(2) }}%</span>
              </div>
            </div>
            <div class="metrics" style="margin-top: 10px">
              <div class="metric">
                <span>磁盘使用量: </span>
                <span class="value">{{ localhostRuntimeData.diskUsage }}MB</span>
              </div>
              <div class="metric">
                <span>网络上行: </span>
                <span class="value">{{ localhostRuntimeData.networkUpload.toFixed(2) }}KB/s</span>
              </div>
            </div>
          </div>
          <div style="font-size: 20px;font-weight: bold;text-align: center;background-color: whitesmoke;margin: 30px;padding: 7px;border-radius: 20px;box-shadow: 0 0 5px 2px #d6d6d6" v-else
          >该服务器上的客户端程序未打开哦</div>
        </div>
        <div style="height: calc((100% - 20px) / 2);background-color: #f5f4f1;border-radius: 20px;box-shadow: inset 0 0 3px #b9b8b8;margin-top: 20px">
          <span style="font-weight: bold;font-size: 30px;margin: 5px 10px">昨日服务器<span style="color: #c6bd1a;">告警</span>:</span>
          <span style="font-weight: bold;font-size: 27px;margin: 0 10px">{{warn.length}}</span>
          <div style="margin: 0 10px;height: calc((100% - 32px))">
            <el-scrollbar :style="{ 'max-height': 'calc(100% - 20px)' }">
              <div class="warn-card" v-for="item in warn" style="position: relative" v-if="warn.length">
                <div style="font-weight: bold">
                  {{item.clientName}}：
                </div>
                <div style="font-size: 15px;color: #747474">
                  {{item.description}}-------- {{ item.time }}
                </div>
              </div>

              <div>
                <div v-if="!warn.length" style="font-size: 17px;color: #747474;text-align: center;margin-top: 20px">
                  昨日暂无告警哦，服务器运行良好
                </div>
              </div>
            </el-scrollbar>
          </div>
        </div>
      </div>
      <div style="flex: 8;background-color: #f5f4f1;margin-left: 20px;border-radius: 10px;box-shadow: inset 0 0 3px #b9b8b8;">
        <div v-if="store.isAdmin">
          <span style="font-weight: bold;font-size: 30px;margin: 5px 10px">在线服务器列表</span>
          <span style="font-weight: bold;font-size: 27px;margin: 0 10px">{{onlineCount}} / {{simpleList.length}}</span>
          <div style="flex-wrap: wrap;gap: 10px">
            <div style="margin: 0 10px">
              <el-scrollbar max-height="350px">
                <div style="display: flex; flex-wrap: wrap; overflow: auto">
                  <div class="overview-client-card" v-for="item in simpleList" style="width: 42%" @click="router.push('/index/manage')">
                    <div style="margin-left: 20px;width: 100%">
                      <div style="font-size: 14px;font-weight: bold">
                        <span :class="`flag-icon flag-icon-${item.location}`"></span>
                        <span style="margin: 0 10px">{{ item.name }}</span>
                        <span style="float: right">
                          <el-tag :type="item.online ? 'success': 'warning'">{{item.online ? '在线':'离线'}}</el-tag>
                        </span>
                      </div>
                      <div style="font-size: 12px;color: grey">
                        操作系统:
                        <i :style="{color: osNameToIcon(item.osName).color}"
                           :class="`fa-brands ${osNameToIcon(item.osName).icon}`"></i>
                        {{ `${item.osName} ${item.osVersion}` }}
                      </div>
                      <div style="font-size: 12px;color: grey">
                        <span style="margin-right: 10px">公网IP: {{ item.ip }}</span>
                      </div>
                    </div>
                  </div>
                </div>
              </el-scrollbar>
            </div>
          </div>
        </div>
        <div v-else>
          <div v-if="subTaskList.length">
            <span style="font-weight: bold;font-size: 30px;margin: 5px 10px">任务列表</span>
            <div style="display: flex;flex-wrap: wrap;gap: 10px">
              <div style="margin: 0 10px">
                <el-scrollbar max-height="350px">
                  <div style="display: flex; flex-wrap: wrap; overflow: auto">
                    <div @click="router.push('/index/devTask')" class="overview-client-card" v-for="item in subTaskList" style="width: 42%">
                      <div>
                        <div style="font-size: 18px;font-weight: bold">
                          <span style="margin: 0 10px">{{ item.name }}</span>
                        </div>
                        <div style="font-size: 12px;color: grey">
                          <span style="margin-right: 10px">任务描述: {{ item.description }}</span>
                        </div>
                        <div style="font-size: 12px;color: grey">
                          <span style="margin-right: 10px">任务状态: {{ item.status === 2 ? '已完成': '未完成' }}</span>
                        </div>
                        <div style="font-size: 12px;color: grey">
                          <span style="margin-right: 10px">任务开始时间: {{ formatDate(item.startTime) }}</span>
                        </div>
                        <div style="font-size: 12px;color: grey">
                          <span style="margin-right: 10px">任务结束时间: {{ formatDate(item.endTime) }}</span>
                        </div>
                      </div>
                    </div>
                  </div>
                </el-scrollbar>
              </div>
            </div>
          </div>
          <div v-else>
            <div style="font-size: 17px;color: #747474;text-align: center;margin-top: 20px">
              目前没有任务分配给你哦~
            </div>
          </div>
        </div>
      </div>
    </div>
    <el-divider style="margin: 45px 0"/>
    <el-scrollbar max-height="500">
      <div style="height: 500px">
        <div style="font-weight: bold;font-size: 22px">AI助手</div>
        <div style="width: 80%; margin: auto;">
          <div v-for="(message, index) in messages" :key="index" :class="message.sender" v-html="message.text">
          </div>
          <div>
            <input v-model="newMessage" @keyup.enter="sendMessage" placeholder="这里输入你想问的问题叭" />
          </div>
        </div>
      </div>
    </el-scrollbar>
  </div>
</template>

<style scoped>
.server-info-card {
  height: calc((100% - 20px) / 2);
  background-image: linear-gradient(to right, #d4eaf7, #b6ccd8);
  border-radius: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
.title {
  font-weight: bold;
  font-size: 30px;
  margin: 5px 10px
}

.metrics {
  display: flex;
  justify-content: space-between;
}

.metric {
  background-color: #f5f5f5;
  padding: 10px;
  border-radius: 10px;
  width: 160px;
}

.value {
  font-weight: bold;
  color: #333;
}

.box-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
  border-radius: 4px;
  overflow: hidden;
}
.overview-client-card {
  border-radius: 5px;
  background-color: #e3e8ea;
  padding: 10px;
  display: flex;
  align-items: center;
  margin: 10px;
}

.warn-card {
  background-color: #e3e8ea;
  margin: 10px;
  border-radius: 10px;
  padding: 10px;
}

.user {
  text-align: right;
  background-color: #dbdbdb;
  margin: 10px;
  padding: 10px;
  border-radius: 10px;
}

.ai {
  text-align: left;
  background-color: #c3dbe6;
  margin: 10px;
  padding: 10px;
  border-radius: 10px;
}

input {
  width: 100%;
  padding: 10px;
  margin-top: 10px;
}
</style>