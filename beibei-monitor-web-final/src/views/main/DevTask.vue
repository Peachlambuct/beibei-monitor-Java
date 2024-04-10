<script setup>

import TaskCard from "@/component/TaskCard.vue";
import DevTaskCard from "@/component/DevTaskCard.vue";
</script>

<template>
  <div>
    <div style="margin: 0 50px">
      <div style="display: flex;justify-content: space-between;align-items: end">
        <div>
          <div style="font-size: 23px;font-weight: bold">开发任务</div>
          <div style="font-size: 15px;color: gray">这里可以查看你的开发任务</div>
        </div>
      </div>
      <el-divider style="margin: 10px 0"/>

      <div style="display: flex; flex-direction: row; flex-wrap: wrap;">
        <DevTaskCard/>
        <DevTaskCard/>
        <DevTaskCard/>
        <DevTaskCard/>
        <DevTaskCard/>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>