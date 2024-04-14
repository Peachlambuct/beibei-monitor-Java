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
  <div style="margin: 0 50px">
    <div style="font-size: 22px;font-weight: bold">
      <el-icon style="height: 100%">
        <svg t="1713072783160" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="11374" width="200" height="200"><path d="M188.523333 192c0 105.917333 84.726667 192 188.872 192s188.872-86.082667 188.872-192c0-105.866667-84.722667-192-188.872-192S188.523333 86.124 188.523333 192z m293.802667 0c0 58.832-47.054667 106.666667-104.933333 106.666667s-104.926667-47.834667-104.926667-106.666667 47.054667-106.666667 104.926667-106.666667 104.933333 47.830667 104.933333 106.666667z m-83.945333 643.66V1024h185.269333L1003.358 597.333333 818.098 408.996zM482.326 938.666667v-67.668L548.888667 938.666667h-66.562667z m125.913333-60.333334L541.676667 810.666667l276.421333-281L884.660667 597.333333zM104.582 682.666667v-42.666667c0-70.585333 56.48-128 125.913333-128H524.291333a123.886667 123.886667 0 0 1 69.882667 21.624l59.394667-60.372C617.83 444.533333 573.278 426.666667 524.291333 426.666667H230.495333C114.787333 426.666667 20.636667 522.372 20.636667 640v128H363.624667l83.941333-85.333333H104.582z" fill="" p-id="11375"></path></svg>
      </el-icon>
      子用户管理</div>
    <div style="font-size: 15px;color: grey">查看子用户配置信息，添加或删除子用户</div>

    <el-divider style="margin: 10px 0"/>

    <div style="display: flex; flex-direction: column; justify-content: center; align-items: center; height: 100%;">
      <div class="info-card" style="flex: 60%;">
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
    </div>

    <el-drawer v-model="createAccount" size="420" :with-header="false">
      <create-sub-account :clients="simpleList" @create="createAccount = false;initSubAccounts()"/>
    </el-drawer>
  </div>



</template>

<style scoped>
.info-card {
  border-radius: 7px;
  padding: 15px 20px;
  background-color: rgba(230, 230, 230, 0.53);
  height: fit-content;

  .title {
    font-size: 18px;
    font-weight: bold;
  }
}

</style>