<script setup>

import {Delete, Edit} from "@element-plus/icons-vue";
import {ref} from "vue";
import {get} from "@/net";
import {ElMessage} from "element-plus";

const emit = defineEmits(['deleteTask','updateTask']);
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

function formatDate(value) {
  if (value) {
    let date = new Date(value);
    let options = { year: 'numeric', month: 'long', day: 'numeric' };
    return date.toLocaleDateString('zh-CN', options);
  }
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
        <div>任务描述：{{data.description}}</div>
        <div>任务小组成员：{{data.principalNames}}</div>
        <div>任务开始时间：{{formatDate(data.startTime)}}</div>
        <div>任务结束时间：{{formatDate(data.endTime)}}</div>
        <div>任务状态：{{data.status===0 ? '未完成' : '已完成'}}</div>
        <div>任务相关服务器：{{data.aboutClientNames ? data.aboutClientNames : "未指定服务器"}}</div>
        <div>任务进度：</div>
        <div v-if="data.process >= 0">
          <el-progress :percentage="data.process * 100" status=""/>
        </div>
        <div v-else>暂未分配子任务</div>
      </div>
    </div>

    <el-dialog v-model="show" style="border-radius: 15px;max-height: 600px" >
      <el-scrollbar max-height="500">
        <div style="font-size: 23px;font-weight: bold">{{data.name}}</div>
        <div style="font-size: 16px;font-weight: bold;margin-top: 10px">
          <div>任务小组成员：</div>
          <ul v-for="item in data.principalNames">
            <li>{{item}}</li>
          </ul>
          <div>任务所在服务器：</div>
          <ul v-for="item in data.aboutClientNames">
            <li>{{item}}</li>
          </ul>
          <div>项目类型：<el-tag>{{data.type}}</el-tag></div>
          <div>项目描述：{{data.description}}</div>
          <div>项目开始时间：{{formatDate(data.startTime)}}</div>
          <div>项目结束时间：{{formatDate(data.endTime)}}</div>
          <div>项目当前状态：<el-tag :type="data.status === 0 ? 'warning':'success'">{{data.status === 0 ? '未完成':'已完成'}}</el-tag></div>
        </div>
        <el-divider style="margin: 20px 0"/>
        <div v-if="data.subtasks.length > 0">
          <div style="font-size: 18px;color: gray;margin-top: 10px">子任务列表</div>
          <ul style="font-size: 14px;margin-top: 10px" v-for="item in data.subtasks">
            <li>项目名称：{{item.name}}</li>
            <li>项目描述：{{item.description}}</li>
            <li>项目开始时间：{{formatDate(item.startTime)}}</li>
            <li>项目结束时间：{{formatDate(item.startTime)}}</li>
            <li>项目当前状态：<el-tag :type="item.status === 0 ? 'warning':'success'">{{item.status === 0 ? '未完成':'已完成'}}</el-tag></li>
          </ul>
        </div>
      </el-scrollbar>
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