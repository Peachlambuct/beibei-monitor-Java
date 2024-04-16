<script setup>

import {Delete} from "@element-plus/icons-vue";
import {ref} from "vue";
import {get} from "@/net";
import {ElMessage} from "element-plus";

const emit = defineEmits(['deleteTask']);
const show = ref(false);

defineProps({
  data: Object
})

function deleteTask(id) {
  get(`/api/task/deleteTask?taskId=${id}`, () => {
    ElMessage.success('删除成功')
    emit('deleteTask')
  })
}
</script>

<template>
  <div class="card">
    <div style="display: flex;justify-content: space-between;margin-top: 10px">
      <div style="margin-left: 20px">
        <div style="font-weight: bold;font-size: 20px">{{data.name}}开发任务</div>
      </div>
      <div class="button-container">
        <el-button style="float: right;margin-right: 10px" :icon="Delete" color="#5c5c5c"
                   @click="deleteTask(data.id)">删除
        </el-button>
      </div>
    </div>
    <el-divider style="margin: 10px 0"/>
    <div>
      <div class="desc" style="margin-left: 20px" @click="show = true">
        <div>任务描述：{{data.description}}</div>
        <div>任务负责人：{{data.principalName}}</div>
        <div>任务开始时间：{{data.startTime}}</div>
        <div>任务结束时间：{{data.endTime}}</div>
        <div>任务状态：已完成</div>
        <div>任务相关服务器：Spark1</div>
        <div>任务进度：</div>
        <el-progress :percentage="10" status=""/>
      </div>
    </div>

    <el-dialog v-model="show" style="border-radius: 15px">
      <div style="font-size: 23px;font-weight: bold">{{data.name}}</div>
      <div style="font-size: 16px;font-weight: bold;margin-top: 10px">
        <div>项目负责人：{{data.principalName}}</div>
        <div>项目类型：{{data.type}}</div>
        <div>项目描述：{{data.description}}</div>
        <div>项目开始时间：{{data.startTime}}</div>
        <div>项目结束时间：{{data.endTime}}</div>
        <div>项目当前状态：<el-tag :type="data.status === 0 ? 'warning':'success'">{{data.status === 0 ? '未完成':'已完成'}}</el-tag></div>
      </div>
      <div v-if="data.subtasks.length > 0">
        <div style="font-size: 18px;color: gray;margin-top: 10px">子任务列表</div>
        <div style="font-size: 16px;font-weight: bold;margin-top: 10px" v-for="item in data.subtasks">
          <div>项目负责人：{{item.principalName}}</div>
          <div>项目描述：{{item.description}}</div>
          <div>项目开始时间：{{item.startTime}}</div>
          <div>项目结束时间：{{item.endTime}}</div>
          <div>项目当前状态：<el-tag :type="item.status === 0 ? 'warning':'success'">{{item.status === 0 ? '未完成':'已完成'}}</el-tag></div>
        </div>
      </div>
    </el-dialog>
  </div>

</template>

<style scoped>
.card {
  background-image: linear-gradient(to right, #f3f3f3, #f8f8f8);
  height: 250px;
  width: 45%;
  border-radius: 10px;
  margin: 16px;


}

.button-container {
  display: flex;
  flex-direction: column;
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