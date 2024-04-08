<script setup>

import {Delete, Plus} from "@element-plus/icons-vue";
import CreateSubAccount from "@/component/CreateSubAccount.vue";
import {ref} from "vue";
import {get} from "@/net";
import {useStore} from "@/store";
import {ElMessage} from "element-plus";

const store = useStore()
const accounts = ref([])
const createAccount = ref(false)
const simpleList = ref([])
if(store.isAdmin) {
  get('/api/monitor/simple-list', list => {
    simpleList.value = list
    initSubAccounts()
  })
}

function deleteAccount(id) {
  get(`/api/user/sub/delete?uid=${id}`, () => {
    ElMessage.success('子账户删除成功')
    initSubAccounts()
  })
}

const initSubAccounts = () =>
    get('/api/user/sub/list', list => accounts.value = list)
</script>

<template>
  <div class="info-card" style="flex: 50%">
    <div class="title"><i class="fa-solid fa-users"></i> 子用户管理</div>
    <el-divider style="margin: 10px 0"/>
    <div v-if="accounts.length" style="text-align: center">
      <div v-for="item in accounts" class="account-card">
        <el-avatar class="avatar" :size="30"
                   src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"/>
        <div style="margin-left: 15px;line-height: 18px;flex: 1">
          <div>
            <span>{{item.username}}</span>
            <span style="font-size: 13px;color: grey;margin-left: 5px">
                管理 {{item.clientList.length}} 个服务器
              </span>
          </div>
          <div style="font-size: 13px;color: grey">{{item.email}}</div>
        </div>
        <el-button type="danger" :icon="Delete"
                   @click="deleteAccount(item.id)" text>删除子账户</el-button>
      </div>
      <el-button :icon="Plus" type="primary"
                 @click="createAccount = true" plain>添加更多子用户</el-button>
    </div>
    <div v-else>
      <el-empty :image-size="100" description="还没有任何子用户哦" v-if="store.isAdmin">
        <el-button :icon="Plus" type="primary" plain
                   @click="createAccount = true">添加子用户</el-button>
      </el-empty>
      <el-empty :image-size="100" description="子账户只能由管理员账号进行操作" v-else/>
    </div>
  </div>
  <el-drawer v-model="createAccount" size="350" :with-header="false">
    <create-sub-account :clients="simpleList" @create="createAccount = false;initSubAccounts()"/>
  </el-drawer>
</template>

<style scoped>
.info-card {
  border-radius: 7px;
  padding: 15px 20px;
  background-color: #fffefb;
  height: fit-content;

  .title {
    font-size: 18px;
    font-weight: bold;
    color: dodgerblue;
  }
}

</style>