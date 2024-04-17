<script setup>

import {Delete} from "@element-plus/icons-vue";

defineProps({
  data: Object
})
</script>

<template>
  <div class="card">
    <div style="margin-left: 20px">
      <div style="font-weight: bold;font-size: 20px">{{data.clientName}}</div>
      <div style="color: gray;font-size: 14px">
        <div>在线状态：<el-tag :type="data.isOnline ? 'success': 'warning'"  >{{data.isOnline ? '在线': '离线'}}</el-tag></div>
        <div>SSH名：{{data.name }}</div>
        <div>IP地址：{{data.ip }}</div>
      </div>
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
  background-image: linear-gradient(to right, rgba(212, 234, 247, 0.6), #d6d6d6);
  height: 120px;
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