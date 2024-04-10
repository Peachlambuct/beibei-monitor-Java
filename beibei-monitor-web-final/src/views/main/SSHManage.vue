<script setup>

import SSHDetailCard from "@/component/SSHDetailCard.vue";
import {Delete, Plus} from "@element-plus/icons-vue";
import {ref} from "vue";

const locations = [
  {name: 'cn', desc: '中国大陆'},
  {name: 'hk', desc: '香港'},
  {name: 'jp', desc: '日本'},
  {name: 'us', desc: '美国'},
  {name: 'sg', desc: '新加坡'},
  {name: 'kr', desc: '韩国'},
  {name: 'de', desc: '德国'}
]
const checkedNodes = ref([])
</script>

<template>

  <div style="margin: 0 50px">
    <div style="display: flex;justify-content: space-between;align-items: end">
      <div>
        <div style="font-size: 25px;font-weight: bold">SSH配置管理</div>
        <div style="font-size: 15px;color: gray">这里管路你的SSH配置管理</div>
      </div>
      <div>
        <el-button color="#b6ccd8" :icon="Plus" type="primary"
                   @click="register.show = true">添加新的SSH连接</el-button>
      </div>
    </div>



    <el-divider style="margin: 10px 0"/>

    <div style="margin-bottom: 20px">
      <el-checkbox-group v-model="checkedNodes">
        <el-checkbox v-for="node in locations" :key="node" :label="node.name" border>
          <span :class="`flag-icon flag-icon-${node.name}`"></span>
          <span style="font-size: 13px;margin-left: 10px">{{node.desc}}</span>
        </el-checkbox>
      </el-checkbox-group>
    </div>

    <div style="display: flex; flex-direction: row; flex-wrap: wrap;">
      <SSHDetailCard/>
      <SSHDetailCard/>
      <SSHDetailCard/>
      <SSHDetailCard/>
      <SSHDetailCard/>
      <SSHDetailCard/>
      <SSHDetailCard/>
      <SSHDetailCard/>
      <SSHDetailCard/>
      <SSHDetailCard/>
      <SSHDetailCard/>
      <SSHDetailCard/>
      <SSHDetailCard/>
      <SSHDetailCard/>
    </div>
  </div>
</template>

<style scoped>

</style>