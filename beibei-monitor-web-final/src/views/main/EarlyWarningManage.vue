<script setup>

import {Plus} from "@element-plus/icons-vue";
import WarnCard from "@/component/WarnCard.vue";
</script>

<template>
  <div style="margin: 0 50px">
    <div style="display: flex;justify-content: space-between;align-items: end">
      <div>
        <div style="font-size: 23px;font-weight: bold">预警规则管理</div>
        <div style="font-size: 15px;color: gray">这里可以管理你预先设置好的预警规则</div>
      </div>
      <div>
        <el-button color="#b6ccd8" :icon="Plus" type="primary"
                   @click="register.show = true">添加新的预警规则</el-button>
      </div>
    </div>
    <el-divider style="margin: 10px 0"/>

    <div style="display: flex; flex-direction: row; flex-wrap: wrap;">
      <WarnCard/>
      <WarnCard/>
      <WarnCard/>
      <WarnCard/>
      <WarnCard/>
    </div>
  </div>
</template>

<style scoped>

</style>