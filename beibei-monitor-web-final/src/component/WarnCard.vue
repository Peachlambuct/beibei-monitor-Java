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
      <el-button style="float: right;margin-right: 10px" :icon="Delete" color="#f5f5e9"
                 @click="register.show = true">删除</el-button>
      <el-button style="float: right;margin-right: 10px;margin-top: 10px" :icon="Delete" color="#f5f5e9"
                 @click="register.show = true">修改预警阈值</el-button>
    </div>
  </div>
</template>

<style scoped>
.card {
  background-image: linear-gradient(to right, #d4eaf7, #d6d6d6);
  height: 110px;
  width: 45%;
  border-radius: 10px;
  margin: 16px;
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