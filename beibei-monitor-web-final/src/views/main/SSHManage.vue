<script setup>

import SSHDetailCard from "@/component/SSHDetailCard.vue";
import {Plus} from "@element-plus/icons-vue";
import {reactive, ref, watch} from "vue";
import {get, post} from "@/net";
import {ElMessage} from "element-plus";

const locations = [
  {name: 'cn', desc: '中国大陆'},
  {name: 'hk', desc: '香港'},
  {name: 'jp', desc: '日本'},
  {name: 'us', desc: '美国'},
  {name: 'sg', desc: '新加坡'},
  {name: 'kr', desc: '韩国'},
  {name: 'de', desc: '德国'}
]
const sshTable = reactive({
  id: '',
  name: '',
  ip: '',
  port: '22',
  username: '',
  password: '',
  clientId: '',
})
const checkedNodes = ref([])
const show = ref(false)
const sshList = ref([])
const serverNames = ref([])

function getServerNames() {
  get('/api/monitor/clientNameList', data => {
    serverNames.value = data
  })
}

function updateSSH(data) {
  sshTable.name = data.name
  sshTable.id = data.id
  sshTable.ip = data.ip
  sshTable.port = data.port
  sshTable.username = data.username
  sshTable.password = data.password
  sshTable.clientId = data.clientId
  show.value = true
}

function getList() {
  get('/api/ssh/list', data => {
    if (checkedNodes.value.length === 0) {
      sshList.value = data;
    } else {
      sshList.value = data.filter(item => checkedNodes.value.includes(item.location));
    }
  })
}

function clearTable() {
  sshTable.id = ''
  sshTable.name = ''
  sshTable.ip = ''
  sshTable.port = '22'
  sshTable.username = ''
  sshTable.password = ''
  sshTable.clientId = ''
}

function save() {
  post('/api/ssh/ssh-save', sshTable, () => {
    ElMessage.success("保存成功")
    getList()
    show.value = false
    clearTable()
  })
}

function handleServerChange(value) {
  const selectedServer = serverNames.value.find(item => item.clientId === value);
  if (selectedServer) {
    sshTable.ip = selectedServer.ip;
  }
}

getList()
getServerNames()
watch(checkedNodes, getList);
</script>

<template>

  <div style="margin: 0 50px">
    <div style="display: flex;justify-content: space-between;align-items: end">
      <div>
        <div style="font-size: 22px;font-weight: bold">
          <el-icon style="height: 100%">
            <svg t="1713072840504" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                 p-id="13380" width="200" height="200">
              <path
                  d="M607.934 417.857c-6.18-6.178-12.766-11.747-19.554-16.91l-0.012 0.011c-6.986-6.72-16.472-10.857-26.93-10.857-21.465 0-38.865 17.4-38.865 38.864a38.697 38.697 0 0 0 9.072 24.947h-0.001a39.02 39.02 0 0 0 9.59 8.256c3.665 3.022 7.262 5.998 10.625 9.361l3.204 3.205c40.28 40.23 28.255 109.54-12.025 149.82l-171.88 171.83c-40.279 40.23-105.762 40.23-146.042 0l-3.23-3.232c-40.281-40.278-40.281-105.81 0-145.99l75.936-75.91c9.742-7.734 15.997-19.67 15.997-33.073 0-23.313-18.899-42.211-42.212-42.211a42.01 42.01 0 0 0-23.725 7.297c-0.021-0.045-0.044-0.088-0.066-0.134l-0.81 0.757a42.455 42.455 0 0 0-8.026 7.51l-78.913 73.842c-74.178 74.23-74.178 195.633 0 269.759l3.204 3.203c74.179 74.127 195.53 74.127 269.708 0l171.83-171.88c74.075-74.175 80.356-191.185 6.281-265.312l-3.156-3.153z"
                  fill="#333333" p-id="13381"></path>
              <path
                  d="M855.62 165.804l-3.204-3.204c-74.178-74.178-195.529-74.178-269.707 0l-171.83 171.88c-74.178 74.178-78.263 181.296-4.085 255.523l3.153 3.104c3.369 3.368 6.866 6.543 10.435 9.589a36.872 36.872 0 0 0 8.993 7.31l0.077 0.062 0.012-0.01a36.508 36.508 0 0 0 18.258 4.87c20.263 0 36.69-16.428 36.69-36.69 0-5.719-1.31-11.132-3.646-15.958-4.85-10.89-13.93-17.52-20.21-23.802l-3.154-3.103c-40.278-40.278-24.983-98.796 15.295-139.074l171.931-171.83c40.18-40.281 105.685-40.281 145.966 0l3.206 3.152c40.279 40.282 40.279 105.839 0 146.068l-75.687 75.738c-10.297 7.629-16.974 19.865-16.974 33.663 0 23.123 18.746 41.87 41.87 41.87a41.668 41.668 0 0 0 21.946-6.211c0.048 0.082 0.093 0.157 0.14 0.24l1.175-1.083a42.09 42.09 0 0 0 9.529-8.793l79.766-73.603c74.233-74.177 74.233-195.53 0.055-269.708z"
                  fill="#333333" p-id="13382"></path>
            </svg>
          </el-icon>
          SSH配置管理
        </div>
        <div style="font-size: 15px;color: gray">这里管路你的SSH配置管理</div>
      </div>
      <div>
        <el-button color="#b6ccd8" :icon="Plus" type="primary"
                   @click="show = true">添加新的SSH连接
        </el-button>
      </div>
    </div>

    <el-dialog v-model="show" @close="clearTable">
      <el-form label-width="100">
        <el-form-item label="SSH服务器">
          <el-select placeholder="请选择服务器" v-model="sshTable.clientId" @change="handleServerChange">
            <el-option v-for="item in serverNames" :key="item.clientId" :label="item.clientName"
                       :value="item.clientId"/>
          </el-select>
        </el-form-item>
        <el-form-item label="SSH名称">
          <el-input v-model="sshTable.name"/>
        </el-form-item>
        <el-form-item label="SSH端口">
          <el-input v-model="sshTable.port"/>
        </el-form-item>
        <el-form-item label="SSH用户名">
          <el-input v-model="sshTable.username"/>
        </el-form-item>
        <el-form-item label="SSH密码">
          <el-input v-model="sshTable.password"/>
        </el-form-item>
        <div class="form-item">
          <el-button @click="save" type="primary">保存</el-button>
        </div>
      </el-form>
    </el-dialog>

    <el-divider style="margin: 10px 0"/>

    <div style="margin-bottom: 20px">
      <el-checkbox-group v-model="checkedNodes">
        <el-checkbox v-for="node in locations" :key="node" :label="node.name" border>
          <span :class="`flag-icon flag-icon-${node.name}`"></span>
          <span style="font-size: 13px;margin-left: 10px">{{ node.desc }}</span>
        </el-checkbox>
      </el-checkbox-group>
    </div>

    <div style="display: flex; flex-direction: row; flex-wrap: wrap;" v-if="sshList.length">
      <SSHDetailCard v-for="item in sshList" :data="item" @flash="getList" @update="updateSSH"/>
    </div>
    <el-empty description="还没有任何SSH连接哦，点击右上角添加一个吧" v-else/>
  </div>
</template>

<style scoped>
.form-item {
  display: flex;
  justify-content: flex-end;
  align-items: flex-end;
}
</style>