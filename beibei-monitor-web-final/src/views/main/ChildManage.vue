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

    <div style="flex: 50%;justify-content: center;align-items: center;display: flex;height: 100%;flex-direction: column;">
      <div class="info-card">
        <div class="title"><i class="fa-solid fa-users"></i> 子用户管理</div>
        <el-divider style="margin: 10px 0"/>
        <div v-if="accounts.length">
          <div v-for="item in accounts" :key="item.id" class="q-my-md row account-card">
            <q-avatar src="https://cdn.quasar.dev/img/avatar.png" />
            <div class="q-ml-md">
              <div>
                <span style="font-weight: bold">{{item.username}}</span>
                <span class="text-caption q-ml-xs">管理 {{item.clientList.length}} 个服务器</span>
              </div>
              <div class="text-caption">{{item.email}}</div>
            </div>
            <div class="row justify-end">
              <q-btn class="q-ml-md" color="negative" @click="deleteAccount(item.id)" icon="delete" label="删除子账户" />
            </div>
          </div>
          <q-btn color="primary" @click="createAccount = true" icon="add" label="添加更多子用户" />
        </div>

        <div v-else>
          <q-card v-if="store.isAdmin" class="q-my-md">
            <q-card-section class="row items-center q-gutter-sm">
              <q-icon name="sentiment_dissatisfied" size="lg" />
              <div>
                <div class="text-h6">还没有任何子用户哦</div>
                <div>点击下方按钮添加子用户</div>
              </div>
            </q-card-section>
            <q-card-section>
              <q-btn color="primary" @click="createAccount = true" icon="add" label="添加子用户" />
            </q-card-section>
          </q-card>
          <q-card v-else class="q-my-md">
            <q-card-section class="row items-center q-gutter-sm">
              <q-icon name="sentiment_dissatisfied" size="lg" />
              <div class="text-h6">子账户只能由管理员账号进行操作</div>
            </q-card-section>
          </q-card>
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
  background-color: rgba(239, 239, 239, 0.93);
  height: fit-content;
  width: 60%;
  margin: 20px 0;

  .title {
    font-size: 18px;
    font-weight: bold;
  }
}

.account-card {
  border-radius: 5px;
  background-color: #e1e1e1;
  padding: 10px;
  display: flex;
  align-items: center;
  text-align: left;
  margin: 10px 0;
  width: 100%;
}

</style>