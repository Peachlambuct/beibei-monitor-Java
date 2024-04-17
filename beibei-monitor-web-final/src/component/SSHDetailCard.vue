<script setup>

import {Delete} from "@element-plus/icons-vue";
import TerminalWindow from "@/component/TerminalWindow.vue";
import {reactive} from "vue";
import {get} from "@/net";
import {ElMessage} from "element-plus";

defineProps({
  data: Object
})
const terminal = reactive({
  show: false,
  id: -1
})
const emit = defineEmits(['flash', 'update'])

function deleteSSH(id) {
  get(`/api/ssh/delete?id=${id}`, () => {
    ElMessage.success("SSH信息删除成功")
    emit('flash')
  })
}
</script>

<template>
  <div class="card">
    <div style="margin-left: 20px" @click="terminal.show = true">
      <div style="font-weight: bold;font-size: 20px">{{data.clientName}}</div>
      <div style="color: gray;font-size: 14px">
        <div>在线状态：<el-tag :type="data.isOnline ? 'success': 'warning'"  >{{data.isOnline ? '在线': '离线'}}</el-tag></div>
        <div>SSH名：{{data.name }}</div>
        <div>IP地址：{{data.ip }}</div>
      </div>
    </div>
    <div class="button-container">
      <el-button style="float: right;margin-right: 10px" color="#f5f5e9" :icon="Delete" type="primary" @click="$emit('update',data)"
                 >修改SSH连接信息</el-button>
      <el-button style="float: right;margin-right: 10px;margin-top: 10px" color="#f5f5e9" :icon="Delete" type="primary"
                 @click="deleteSSH(data.id)">删除SSH连接信息</el-button>
    </div>
    <el-drawer style="width: 800px" :size="520" direction="btt"
               @close="terminal.id = -1"
               v-model="terminal.show" :close-on-click-modal="false">
      <template #header>
        <div>
          <div style="font-size: 18px;color: #96b0cd;font-weight: bold;">SSH远程连接</div>
          <div style="font-size: 14px">
            远程连接的建立将由服务端完成，因此在内网环境下也可以正常使用。
          </div>
        </div>
      </template>
      <terminal-window :id="data.id" :data="data"/>
    </el-drawer>
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
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
.button-container {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  margin-right: 10px;
}
</style>