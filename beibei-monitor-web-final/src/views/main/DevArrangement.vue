<script setup>
import {ref, watch} from "vue";
import {get} from "@/net";
import moment from "moment/moment";

const taskList = ref([])
const show = ref(false)
const checkedNodes = ref([])
const selectedTasks = ref([])
let taskTypes = []

function getPage() {
  get('/api/task/getAllSubtask', data => {
    taskList.value = data.map(task => {
      task.startTime = moment(task.startTime).format('YYYY-MM-DD')
      task.endTime = moment(task.endTime).format('YYYY-MM-DD')
      if (task.updateTime){
        task.updateTime = moment(task.updateTime).format('YYYY-MM-DD')
      }else {
        task.updateTime = '无'
      }

      return task;
    });
    taskList.value.sort((a, b) => new Date(b.startTime) - new Date(a.startTime));
    taskTypes = Array.from(new Set(taskList.value.map(task => task.taskType)));
    checkedNodes.value = taskTypes;
    selectedTasks.value = taskList.value;  // 初始时显示所有任务
    console.info(checkedNodes.value)
  })
}

getPage()

watch(checkedNodes, (newVal) => {
  if (newVal.length === taskTypes.length) {
    selectedTasks.value = taskList.value;  // 如果所有复选框都被选中，显示所有任务
  } else {
    selectedTasks.value = taskList.value.filter(task => newVal.includes(task.taskType));  // 否则，只显示选中的任务类型的任务
  }
});

const item = ref()

function initItem(data) {
  item.value = data
  show.value = true
}
</script>

<template>
  <div style="margin: 0 50px">
    <div style="font-size: 22px;font-weight: bold">
      <el-icon style="height: 100%">
        <svg t="1713072943090" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
             p-id="17599" width="200" height="200">
          <path
              d="M449.408 896H192a64 64 0 0 1-64-64V192a64 64 0 0 1 64-64h576a64 64 0 0 1 64 64v219.008c23.04 10.112 44.096 23.424 64 38.464V192A128 128 0 0 0 768 64H192A128 128 0 0 0 64 192v640a128 128 0 0 0 128 128h321.728a322.432 322.432 0 0 1-64.32-64z"
              fill="" p-id="17600"></path>
          <path
              d="M704 448a256 256 0 1 0 0 512 256 256 0 0 0 0-512z m0 447.872a191.872 191.872 0 1 1 0-383.808 191.872 191.872 0 0 1 0 383.808z"
              fill="" p-id="17601"></path>
          <path
              d="M800 672h-64v-32a32 32 0 0 0-64 0v64a32 32 0 0 0 32 32h96a32 32 0 0 0 0-64zM438.848 265.216a31.808 31.808 0 0 0-44.992 0L287.232 371.84l-42.688-42.688a30.72 30.72 0 1 0-43.456 43.456l59.584 59.584c1.344 2.304 2.432 4.672 4.416 6.656 6.656 6.592 15.36 9.408 23.936 9.088a30.848 30.848 0 0 0 21.824-9.024c0.704-0.704 1.024-1.6 1.6-2.432l126.4-126.336a31.744 31.744 0 0 0 0-44.928zM544 320a32 32 0 0 0 0 64h192a32 32 0 0 0 0-64H544zM393.856 489.216L287.232 595.84l-42.688-42.688a30.72 30.72 0 1 0-43.456 43.456l59.584 59.584c1.344 2.304 2.432 4.672 4.416 6.656 6.656 6.592 15.36 9.408 23.936 9.088a30.848 30.848 0 0 0 21.824-9.024c0.704-0.704 1.024-1.6 1.6-2.432l126.4-126.336a31.872 31.872 0 0 0-44.992-44.928z"
              fill="" p-id="17602"></path>
        </svg>
      </el-icon>
      开发安排
    </div>
    <div style="font-size: 15px;color: gray">这里查看管理员下达的任务安排</div>
    <el-divider style="margin: 10px 0"/>
    <div v-if="taskList.length">
      <div style="margin-bottom: 20px">
        <el-checkbox-group v-model="checkedNodes">
          <el-checkbox v-for="node in taskTypes" :key="node" :label="node" border>
            <span style="font-size: 13px;margin-left: 10px">{{ node }}</span>
          </el-checkbox>
        </el-checkbox-group>
      </div>

      <el-timeline style="max-width: 100%">
        <el-timeline-item style="width: 70%" v-for="item in selectedTasks" :key="item" :timestamp="item.startTime"
                          placement="top">
          <el-card class="taskCard" @click="initItem(item)" style="border-radius: 20px">
          <span style="display: flex; align-items: center;margin: 20px 0">
            <span style="font-weight: bold;font-size: 25px">{{ item.name }}</span>
            <el-tag style="margin-left: 40px">{{ item.taskType }}</el-tag>
          </span>
            <p>任务描述：{{ item.description }}</p>
            <p>开发周期：{{ item.startTime }} 至 {{ item.endTime }}</p>
            <p>项目进度：{{ item.status === 0 ? '进行中...' : '已完成' }}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>

      <el-dialog v-model="show" title="任务详情" style="border-radius: 10px">
        <el-form label-position="left" label-width="120px">
          <el-form-item label="任务名称:">
            {{ item.name }}
          </el-form-item>
          <el-form-item label="任务描述:">
            {{ item.description }}
          </el-form-item>
          <el-form-item label="任务类型:">
            {{ item.taskType }}
          </el-form-item>
          <el-form-item label="开始时间:">
            {{ item.startTime }}
          </el-form-item>
          <el-form-item label="结束时间:">
            {{ item.endTime }}
          </el-form-item>
          <el-form-item label="更新时间:">
            {{ item.updateTime ? item.updateTime : '无' }}
          </el-form-item>
          <el-form-item label="更新用户:">
            {{ item.updateUserName ? item.updateUserName : '无' }}
          </el-form-item>
          <el-form-item label="状态:">
            <el-tag type="warning" v-if="item.status === 0">进行中...</el-tag>
            <el-tag type="success" v-else>已完成</el-tag>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
    <el-empty description="还没有任何任务哦" v-else/>

  </div>
</template>

<style scoped>
.taskCard {
  background-image: linear-gradient(to right, rgba(212, 234, 247, 0.6), #d6d6d6);
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  transition: .3s;

  &:hover {
    cursor: pointer;
    transform: scale(1.03);
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
  }
}
</style>