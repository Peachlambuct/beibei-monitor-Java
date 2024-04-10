<script setup>

import {
  Finished, Moon,
  Setting, Sunny,
  SwitchFilled,
} from "@element-plus/icons-vue";
import router from "@/router/index.js";
import {logout} from "@/net/index.js";
import {useStore} from "@/store";
import {useDark} from "@vueuse/core";
import {ref} from "vue";

function userLogout() {
  logout(() => {
    router.push('/')
  })
}

const store = useStore()
const dark = ref(useDark())

</script>

<template>
  <el-container class="back_ground">
    <el-header style="background-color: #cccbc8;width: 100%" height="40px">
      <el-switch style="margin: 0 20px;float: right"
                 v-model="dark" active-color="#424242"
                 :active-action-icon="Moon"
                 :inactive-action-icon="Sunny"/>
    </el-header>
    <el-container class="center">
      <el-aside width="230">
        <div class="left-box">
          <div class="avatar">
            <el-avatar
                size="large"
                src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
            />
            <div>
              <el-text style="font-size: larger;font-weight: bold;color: #181818">{{ store.user.username }}</el-text>
            </div>
            <div>
              <el-text style="font-size: small;font-weight: lighter;color: gray">{{store.user.email}}</el-text>
            </div>
          </div>
          <div style="height: calc(100% - 225px);margin-top: 20px">
            <el-scrollbar style="height: 100%">
              <el-menu
                  router
                  :default-active="$route.path"
                  background-color="#fffefb"
                  style="height: 100%;width: 100%"
              >
                <el-sub-menu index="1">
                  <template #title>
                    <el-icon class="menu-icon">
                      <SwitchFilled/>
                    </el-icon>
                    服务器管理
                  </template>
                  <el-menu-item index="/index">
                    <el-icon class="menu-icon">
                      <Finished/>
                    </el-icon>
                    <template #title>服务器总览</template>
                  </el-menu-item>

                  <el-menu-item index="/index/security">
                    <el-icon class="menu-icon">
                      <Finished/>
                    </el-icon>
                    <template #title>账户安全</template>
                  </el-menu-item>

                  <el-menu-item index="/index/manage">
                    <el-icon class="menu-icon">

                    </el-icon>
                    <template #title>资源管理</template>
                  </el-menu-item>
                </el-sub-menu>

                <el-sub-menu index="2">
                  <template #title>
                    <el-icon class="menu-icon">
                      <SwitchFilled/>
                    </el-icon>
                    服务器安全
                  </template>
                  <el-menu-item index="/index/earlyWarning">
                    <el-icon class="menu-icon">

                    </el-icon>
                    <template #title>预警提醒</template>
                  </el-menu-item>

                  <el-menu-item index="/index/child">
                    <el-icon class="menu-icon">

                    </el-icon>
                    <template #title>子用户管理</template>
                  </el-menu-item>
                </el-sub-menu>


                <el-sub-menu index="3">
                  <template #title>
                    <el-icon class="menu-icon">

                    </el-icon>
                    运行维护
                  </template>

                  <el-menu-item index="/index/sshManage">
                    <el-icon class="menu-icon">

                    </el-icon>
                    <template #title>SSH</template>
                  </el-menu-item>

                  <el-menu-item index="/index/taskAssignment">
                    <el-icon class="menu-icon">

                    </el-icon>
                    <template #title>任务分配</template>
                  </el-menu-item>

                </el-sub-menu>

                <el-sub-menu index="4">
                  <template #title>
                    <el-icon class="menu-icon">

                    </el-icon>
                    开发相关
                  </template>

                  <el-menu-item index="/index/devArrangement">
                    <el-icon class="menu-icon">

                    </el-icon>
                    <template #title>开发安排</template>
                  </el-menu-item>

                  <el-menu-item index="/index/devTask">
                    <el-icon class="menu-icon">

                    </el-icon>
                    <template #title>开发任务</template>
                  </el-menu-item>
                </el-sub-menu>
              </el-menu>
            </el-scrollbar>
          </div>
          <div style="text-align: center">
            <el-button style="background-color:#fffefb" text @click="userLogout">
              <el-icon>
                <Setting/>
              </el-icon>
              Log out
            </el-button>
          </div>
        </div>
      </el-aside>
      <el-main class="right-box">
        <el-scrollbar height="100%">
          <router-view/>
        </el-scrollbar>
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>


.back_ground {
  height: 100vh;
  width: 100vw;
  background-color: #fffefb;
  display: flex;
  justify-content: center;
  align-items: center;
}

.center {
  width: calc(100% - 74px);
  height: calc(100% - 54px);
  display: flex;
}

.left-box {
  width: 230px;
  height: 100%;
}

.right-box {
  width: calc(100% - 220px);
  height: 100%;
  padding: 0;
}

.avatar {
  text-align: center;
  margin-top: 25px;
  height: 120px;
}

.menu-icon {
  background-color: #d4eaf7;
  height: 35px;
  width: 35px;
  border-radius: 10px
}

:deep(.el-aside) {
  overflow: hidden;
}

</style>