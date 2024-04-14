<script setup>

import {Delete} from "@element-plus/icons-vue";
</script>

<template>
  <div class="card">
    <div style="margin-left: 20px">
      <div style="font-weight: bold;font-size: 20px">XXX服务器</div>
      <div>在线状态：在线</div>
      <div>CPU：80 | Memory：20</div>
    </div>
    <div class="button-container">
      <el-button style="float: right;margin-right: 10px" color="#f5f5e9" :icon="Delete" type="primary"
                 @click="register.show = true">SSH连接</el-button>
      <el-button style="float: right;margin-right: 10px;margin-top: 10px" color="#f5f5e9" :icon="Delete" type="primary"
                 @click="register.show = true">删除SSH连接信息</el-button>
    </div>
  </div>
</template>

<style scoped>
.card {
  background-image: linear-gradient(to right, #d4eaf7, #d6d6d6);
  height: 100px;
  width: 45%;
  border-radius: 10px;
  margin: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.button-container {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  margin-right: 10px;
}
</style>