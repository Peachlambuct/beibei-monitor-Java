<script setup>
import {ref} from "vue";

const locations = [
  {name: 'dev', desc: '开发'},
  {name: 'wh', desc: '维护'},
  {name: 'cs', desc: '测试'},
  {name: 'sj', desc: '设计'},
  {name: 'wx', desc: '维修'},
]
const checkedNodes = ref([])
</script>

<template>
  <div style="margin: 0 50px">
    <div style="font-size: 24px;font-weight: bold">开发安排</div>
    <div style="font-size: 15px;color: gray">这里查看开发安排</div>
    <el-divider style="margin: 10px 0"/>

    <div style="margin-bottom: 20px">
      <el-checkbox-group v-model="checkedNodes">
        <el-checkbox v-for="node in locations" :key="node" :label="node.name" border>
          <span style="font-size: 13px;margin-left: 10px">{{node.desc}}</span>
        </el-checkbox>
      </el-checkbox-group>
    </div>

    <el-timeline style="max-width: 600px">
      <el-timeline-item timestamp="2018/4/12" placement="top">
        <el-card class="taskCard">
          <h4>Update Github template</h4>
          <p>Tom committed 2018/4/12 20:46</p>
        </el-card>
      </el-timeline-item>
      <el-timeline-item timestamp="2018/4/3" placement="top">
        <el-card class="taskCard">
          <h4>Update Github template</h4>
          <p>Tom committed 2018/4/3 20:46</p>
        </el-card>
      </el-timeline-item>
      <el-timeline-item timestamp="2018/4/2" placement="top">
        <el-card class="taskCard">
          <h4>Update Github template</h4>
          <p>Tom committed 2018/4/2 20:46</p>
        </el-card>
      </el-timeline-item>
      <el-timeline-item timestamp="2018/4/1" placement="top">
        <el-card class="taskCard">
          <h4>Update Github template</h4>
          <p>Tom committed 2018/4/2 20:46</p>
        </el-card>
      </el-timeline-item>
      <el-timeline-item timestamp="2018/3/2" placement="top">
        <el-card class="taskCard">
          <h4>Update Github template</h4>
          <p>Tom committed 2018/4/2 20:46</p>
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<style scoped>
.taskCard {
  background-image: linear-gradient(to right, #d4eaf7, #d6d6d6);
}
</style>