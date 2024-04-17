<script setup>

import {Delete} from "@element-plus/icons-vue";
import {percentageToStatus} from "@/tools";

defineProps({
  data: Object
})
</script>

<template>
  <div class="card">
    <div style="display: flex;justify-content: space-between;margin-top: 10px">
      <div style="margin-left: 20px">
        <div style="font-weight: bold;font-size: 20px">{{data.name}}</div>
      </div>
      <div class="button-container">
        <el-button style="float: right;margin-right: 10px" :icon="Delete" color="#5c5c5c"
                   @click="register.show = true">编辑
        </el-button>
      </div>
    </div>
    <el-divider style="margin: 10px 0"/>
    <div>
      <div class="desc" style="margin-left: 20px">
        <div>任务描述：{{data.description}}</div>
        <div>任务小组成员：{{data.principalNames}}</div>
        <div>任务开始时间：{{ data.startTime }}</div>
        <div>任务结束时间：{{ data.endTime }}</div>
        <div>任务状态：未完成</div>
        <div>任务相关服务器：{{data.aboutClientNames}}</div>
        <div>任务进度：</div>
        <el-progress :percentage="data.process * 100" status=""/>
        <p>{{data}}</p>
      </div>
    </div>
  </div>

</template>

<style scoped>
.card {
  background-image: linear-gradient(to right, #efeeee, #f8f8f8);
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
  overflow: auto;
  max-height: 200px;
  height: 200px;
}
</style>