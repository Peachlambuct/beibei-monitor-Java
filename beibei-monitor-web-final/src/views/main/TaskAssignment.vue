<script setup>

import {Plus} from "@element-plus/icons-vue";
import moment from 'moment';
import TaskCard from "@/component/TaskCard.vue";
import {ref, watch} from "vue";
import {get, post} from "@/net";
import {ElMessage} from "element-plus";
import {osNameToIcon} from "@/tools";
import {useStore} from "@/store";

const show = ref(false);

let mainTask = ref({
  name: '',
  type: '',
  description: '',
  principalIds: [],
  startTime: '',
  endTime: '',
  aboutClientId: [],
  subtasks: []
});
const store = useStore();
const taskList = ref([])
const simpleList = ref([])
const accounts = ref([])
const isChecked = (id, list) => {
  list = list || [];
  const checked = ref(list.includes(id));
  watch(checked, (newVal) => {
    const index = list.indexOf(id);
    if (newVal && index === -1) {
      list.push(id);
    } else if (!newVal && index !== -1) {
      list.splice(index, 1);
    }
  });
  return checked;
};
if(store.isAdmin) {
  get('/api/monitor/simple-list', list => {
    simpleList.value = list
  })
}
function getTaskList() {
  get("/api/task/list", (res) => {
    res.forEach(item => {
      item.startTime = moment(item.startTime).format('YYYY-MM-DD HH:mm:ss')
      item.endTime = moment(item.endTime).format('YYYY-MM-DD HH:mm:ss')
      item.subtasks.forEach(subTask => {
        subTask.startTime = moment(subTask.startTime).format('YYYY-MM-DD HH:mm:ss')
        subTask.endTime = moment(subTask.endTime).format('YYYY-MM-DD HH:mm:ss')
      })
    })
    taskList.value = res
    console.info("服务器获取" + res)
  })
}
getTaskList()
get('/api/user/sub/list', list => accounts.value = list)
function addSubTask() {
  mainTask.value.subtasks.push({
    name: '',
    description: '',
    startTime: '',
    endTime: '',
  });
}

function addTask() {
  post("/api/task/saveTask", mainTask.value, (res) => {
    ElMessage.success("添加/修改成功")
    getTaskList()
    mainTask.value = {
      name: '',
      type: '',
      description: '',
      principalIds: [],
      startTime: '',
      endTime: '',
      aboutClientId: [],
      subtasks: []
    }
  },(e) => {
    ElMessage.error(e)
    getTaskList()
  })
  show.value = false
}

function removeSubTask(index) {
  mainTask.value.subtasks.splice(index, 1)
}

function updateTask(data) {
  mainTask.value = data
  mainTask.value.aboutClientId = data.aboutClientIds
  show.value = true
}

function initTable() {
  show.value = true
  mainTask.value = {
    name: '',
    type: '',
    description: '',
    principalIds: [],
    startTime: '',
    endTime: '',
    aboutClientId: [],
    subtasks: []
  }
}

</script>

<template>
  <div style="margin: 0 50px">
    <div style="display: flex;justify-content: space-between;align-items: end">
      <div>
        <div style="font-size: 22px;font-weight: bold">
          <el-icon style="height: 100%">
            <svg t="1713072867638" class="icon" viewBox="0 0 1091 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="14399" width="200" height="200"><path d="M615.164179 477.61194h338.968017c38.208955 0 69.321962-31.113006 69.321962-69.321961V69.321962c0-38.208955-31.113006-69.321962-69.321962-69.321962h-338.968017c-38.208955 0-69.321962 31.113006-69.321962 69.321962v338.968017c0 38.208955 31.113006 69.321962 69.321962 69.321961zM408.289979 545.842217H69.321962c-38.208955 0-69.321962 31.113006-69.321962 69.321962v338.968017c0 38.208955 31.113006 69.321962 69.321962 69.321962h338.968017c38.208955 0 69.321962-31.113006 69.321961-69.321962v-338.968017c0-38.208955-31.113006-69.321962-69.321961-69.321962zM1072.579957 635.906183c-26.200426-27.837953-69.867804-28.929638-97.705757-2.729211l-3.275053 3.275054-198.686567 207.965884-108.622601-111.351812c-28.383795-25.654584-72.051173-23.471215-98.251599 4.91258-1.091684 1.091684-2.183369 2.183369-2.729212 3.275053-22.925373 28.383795-22.925373 68.776119 0 97.159915l156.656717 163.752665c26.200426 27.837953 70.413646 28.929638 98.251599 2.729211 1.091684-1.091684 2.183369-1.637527 2.729211-2.729211l248.904051-260.366737c28.383795-29.47548 29.47548-75.326226 2.729211-105.893391zM408.289979 0H69.321962C31.113006 0 0 31.113006 0 69.321962v338.968017c0 38.208955 31.113006 69.321962 69.321962 69.321961h338.968017c38.208955 0 69.321962-31.113006 69.321961-69.321961V69.321962c0-38.208955-31.113006-69.321962-69.321961-69.321962z" p-id="14400"></path></svg>
          </el-icon>
          任务分配</div>
        <div style="font-size: 15px;color: gray">这里可以管理你下发的任务以及查看进度</div>
      </div>
      <div>
        <el-button color="#b6ccd8" :icon="Plus" type="primary"
                   @click="initTable">添加新的任务</el-button>
      </div>
    </div>
    <el-divider style="margin: 10px 0"/>

    <el-dialog v-model="show" width="600">
      <div style="display: flex; flex-direction: column">
        <div style="font-size: 24px;color: #606060">主任务</div>
        <el-form style="margin-top: 10px">
          <el-input v-model="mainTask.name" placeholder="请输入任务名称" style="margin-bottom: 10px"/>
          <el-input v-model="mainTask.type" placeholder="请输入任务类型" style="margin-bottom: 10px"/>
          <el-input v-model="mainTask.description" placeholder="请输入任务描述" style="margin-bottom: 10px"/>
          <el-date-picker
              v-model="mainTask.startTime"
              type="date"
              placeholder="任务开始日期"
              value-format="YYYY-MM-DD HH:mm:ss"
              style="margin-bottom: 10px"
          ></el-date-picker>
          <el-date-picker
              v-model="mainTask.endTime"
              type="date"
              placeholder="任务结束日期"
              value-format="YYYY-MM-DD HH:mm:ss"
              style="margin-bottom: 10px;margin-left: 10px"
          ></el-date-picker>
          <div style="font-weight: bold">项目小组成员</div>
          <el-scrollbar max-height="200">
            <div class="client-card" v-for="item in accounts">
              <el-checkbox v-model="isChecked(item.id, mainTask.principalIds).value"/>
              <div style="margin-left: 20px">
                <div style="font-size: 14px;font-weight: bold">
                  <span>{{ item.username }}</span>
                </div>
                <div style="font-size: 12px;color: grey">
                  <span style="margin-right: 10px">邮箱: {{item.email}}</span>
                </div>
              </div>
            </div>
          </el-scrollbar>
          <div style="font-weight: bold">项目所在服务器</div>
          <el-scrollbar max-height="200">
            <div class="client-card" v-for="item in simpleList">
              <el-checkbox v-model="isChecked(item.id, mainTask.aboutClientId).value"/>
              <div style="margin-left: 20px">
                <div style="font-size: 14px;font-weight: bold">
                  <span :class="`flag-icon flag-icon-${item.location}`"></span>
                  <span style="margin: 0 10px">{{ item.name }}</span>
                </div>
                <div style="font-size: 12px;color: grey">
                  操作系统:
                  <i :style="{color: osNameToIcon(item.osName).color}"
                     :class="`fa-brands ${osNameToIcon(item.osName).icon}`"></i>
                  {{`${item.osName} ${item.osVersion}`}}
                </div>
                <div style="font-size: 12px;color: grey">
                  <span style="margin-right: 10px">公网IP: {{item.ip}}</span>
                </div>
              </div>
            </div>
          </el-scrollbar>
        </el-form>
        <div style="font-size: 20px;color: gray">{{mainTask.subtasks.length > 0 ? '子任务列表': ''}}</div>
        <el-form style="margin-top: 10px">
          <div v-for="(subTask, index) in mainTask.subtasks" :key="index" style="margin-top: 40px">
            <el-input v-model="subTask.name" placeholder="请输入任务名称" style="margin-bottom: 10px"/>
            <el-input v-model="subTask.description" placeholder="请输入任务描述" style="margin-bottom: 10px"/>
            <el-date-picker
                v-model="subTask.startTime"
                type="date"
                placeholder="选择日期"
                value-format="YYYY-MM-DD HH:mm:ss"
                style="margin-bottom: 10px"
            ></el-date-picker>
            <el-date-picker
                v-model="subTask.endTime"
                type="date"
                placeholder="选择日期"
                value-format="YYYY-MM-DD HH:mm:ss"
                style="margin-bottom: 10px;margin-left: 10px"
            ></el-date-picker>
            <el-button style="float: right" type="primary" @click="removeSubTask(index)">删除</el-button>
          </div>
        </el-form>
        <div class="button-container">
          <el-button @click="addSubTask" type="primary">添加子任务</el-button>
          <el-button @click="addTask" type="success">提交</el-button>
          <el-button type="danger">取消</el-button>
        </div>
      </div>
    </el-dialog>

    <div style="display: flex; flex-wrap: wrap;" v-if="taskList.length">
      <q-intersection
          v-for="item in taskList"
          transition="scale"
          class="example-item"
          v-if="taskList.length"
          style="width: 50%"
      >
        <TaskCard @update-task="updateTask" @delete-task="getTaskList" :data="item"/>
      </q-intersection>
    </div>
    <el-empty description="还没有任何任务哦" v-else/>
  </div>
</template>

<style scoped>
.button-container {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

.client-card {
  border-radius: 5px;
  background-color: var(--el-bg-color-page);
  padding: 10px;
  display: flex;
  align-items: center;
  margin: 10px;
}
</style>