<script setup>

import {Delete, Edit} from "@element-plus/icons-vue";
import {reactive, ref} from "vue";
import {get} from "@/net";
import {ElMessage} from "element-plus";

const emit = defineEmits(['deleteTask', 'updateTask']);
const show = ref(false);

const info = defineProps({
  data: Object
})
const projectDate = reactive({
  'from': formatDateTo(info.data.startTime),
  'to': formatDateTo(info.data.endTime)
})
console.info(projectDate)

function deleteTask(id) {
  get(`/api/task/deleteTask?taskId=${id}`, () => {
    ElMessage.success('删除成功')
    emit('deleteTask')
  })
}

function formatDate(value) {
  if (value) {
    let date = new Date(value);
    let options = {year: 'numeric', month: 'long', day: 'numeric'};
    return date.toLocaleDateString('zh-CN', options);
  }
}

function formatDateTo(date) {
  if (!(date instanceof Date)) {
    date = new Date(date);
  }
  const year = date.getFullYear();
  const month = date.getMonth() + 1; // getMonth() 返回的月份从 0 开始
  const day = date.getDate();

  return `${year}/${month < 10 ? '0' + month : month}/${day < 10 ? '0' + day : day}`;
}

</script>

<template>
  <div class="card">
    <div style="display: flex;justify-content: space-between;margin-top: 10px">
      <div style="margin-left: 20px">
        <div style="font-weight: bold;font-size: 20px">{{data.name}}</div>
      </div>
      <div class="button-container">
        <el-button style="float: right;margin-right: 10px" :icon="Edit" color="#5c5c5c"
                   @click="$emit('updateTask',data)">修改
        </el-button>
        <el-button style="float: right;margin-right: 10px" :icon="Delete" color="#5c5c5c"
                   @click="deleteTask(data.id)">删除
        </el-button>
      </div>
    </div>
    <el-divider style="margin: 10px 0"/>
    <div>
      <div class="desc" style="margin-left: 20px" @click="show = true">
        <div>任务描述：{{ data.description }}</div>
        <div>任务小组成员：{{ data.principalNames }}</div>
        <div>任务开始时间：{{ formatDate(data.startTime) }}</div>
        <div>任务结束时间：{{ formatDate(data.endTime) }}</div>
        <div>任务状态：{{ data.status === 0 ? '未完成' : '已完成' }}</div>
        <div>任务相关服务器：{{ data.aboutClientNames ? data.aboutClientNames : "未指定服务器" }}</div>
        <div>任务进度：</div>
        <div v-if="data.process >= 0">
          <el-progress :percentage="data.process * 100" status=""/>
        </div>
        <div v-else>暂未分配子任务</div>
      </div>
    </div>

    <q-dialog full-width v-model="show" transition-show="rotate" transition-hide="rotate">
      <q-card style="width: 760px;padding: 30px;">
        <div style="padding: 30px; display: flex; justify-content: space-between;">
          <div style="flex: 1">
            <div style="font-size: 23px;font-weight: bold">{{ data.name }}</div>
            <div style="font-size: 16px;font-weight: bold;margin-top: 10px">
              <div>任务小组成员：</div>
              <q-list bordered separator>
                <q-item clickable v-ripple v-for="item in data.principalNames" :key="item">
                  <q-item-section>{{ item }}</q-item-section>
                </q-item>
              </q-list>
              <div>任务所在服务器：</div>
              <q-list bordered separator>
                <q-item clickable v-ripple v-for="item in data.aboutClientNames" :key="item">
                  <q-item-section>{{ item }}</q-item-section>
                </q-item>
              </q-list>
              <div>项目类型：
                <el-tag>{{ data.type }}</el-tag>
              </div>
              <div>项目描述：{{ data.description }}</div>
              <div>项目开始时间：{{ formatDate(data.startTime) }}</div>
              <div>项目结束时间：{{ formatDate(data.endTime) }}</div>
              <div>项目当前状态：
                <el-tag :type="data.status === 0 ? 'warning':'success'">{{ data.status === 0 ? '未完成' : '已完成' }}
                </el-tag>
              </div>
            </div>
            <div v-if="data.subtasks.length > 0">
              <div style="font-size: 18px;color: gray;margin-top: 10px">子任务列表</div>
              <ul style="font-size: 14px;margin-top: 10px" v-for="item in data.subtasks">
                <li>项目名称：{{ item.name }}</li>
                <li>项目描述：{{ item.description }}</li>
                <li>项目开始时间：{{ formatDate(item.startTime) }}</li>
                <li>项目结束时间：{{ formatDate(item.startTime) }}</li>
                <li>项目当前状态：
                  <el-tag :type="item.status === 0 ? 'warning':'success'">{{ item.status === 0 ? '未完成' : '已完成' }}
                  </el-tag>
                </li>
              </ul>
            </div>
          </div>
          <div style="flex: 1; margin-left: 20px;">
            <q-date v-model="projectDate" color="indigo-12"/>
          </div>
        </div>
        <q-card-actions align="right">
          <q-btn flat label="确定" color="primary" v-close-popup/>
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>

</template>

<style scoped>
.card {
  background-image: linear-gradient(to right, #f3f3f3, #f8f8f8);
  height: 250px;
  border-radius: 10px;
  margin: 16px;
  padding: 10px;

  transition: .3s;

  &:hover {
    cursor: pointer;
    transform: scale(1.05);
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
  }
}

.button-container {
  display: flex;
  align-items: flex-end;
  margin-right: 10px;
}

.desc {
  div {
    color: #5c5c5c;
    font-size: 15px;
  }
}
</style>