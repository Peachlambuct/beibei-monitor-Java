<script setup>

import DevTaskCard from "@/component/DevTaskCard.vue";
import {ref} from "vue";
import {get} from "@/net";

const taskList = ref([])
function getList() {
  get('/api/task/list', data => {
    taskList.value = data.map(task => {
      const startDate = new Date(task.startTime);
      const endDate = new Date(task.endTime);
      task.startTime = startDate.toLocaleDateString('en-CA');
      task.endTime = endDate.toLocaleDateString('en-CA');
      return task;
    });
  })
}

getList()
</script>

<template>
  <div>
    <div style="margin: 0 50px">
      <div style="display: flex;justify-content: space-between;align-items: end">
        <div>
          <div style="font-size: 22px;font-weight: bold">
            <el-icon style="height: 100%">
              <svg t="1713072977896" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="19178" width="200" height="200"><path d="M829.121 0H255.879C188.015 0 133 55.014 133 122.878V266H72v82h61v123H72v82h61v123H72v82h61v143.122C133 968.985 188.015 1024 255.879 1024h573.242C896.985 1024 952 968.985 952 901.122V122.878C952 55.014 896.985 0 829.121 0zM255.959 942C233.338 942 215 923.662 215 901.041V758h61v-82h-61V553h61v-82h-61V348h61v-82h-61V122.959C215 100.338 233.338 82 255.959 82H666v860H255.959zM870 901.041C870 923.662 851.662 942 829.041 942H748V82h81.041C851.662 82 870 100.338 870 122.959v778.082z" fill="#040000" p-id="19179"></path></svg>
            </el-icon>
            开发任务</div>
          <div style="font-size: 15px;color: gray">这里可以查看你的开发任务</div>
        </div>
      </div>
      <el-divider style="margin: 10px 0"/>

      <div style="display: flex; flex-direction: row; flex-wrap: wrap;">
        <DevTaskCard v-for="item in taskList" :data="item" @flash="getList"/>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>