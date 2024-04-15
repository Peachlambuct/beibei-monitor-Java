<script setup>
import {ref} from "vue";

const locations = [
  {name: 'dev', desc: '开发'},
  {name: 'wh', desc: '维护'},
  {name: 'cs', desc: '测试'},
  {name: 'sj', desc: '设计'},
  {name: 'wx', desc: '维修'},
]

const time = [
  "2018/4/12","2018/4/11","2018/4/10","2018/3/9","2018/2/8"
]
const checkedNodes = ref([])
</script>

<template>
  <div style="margin: 0 50px">
    <div style="font-size: 22px;font-weight: bold">
      <el-icon style="height: 100%">
        <svg t="1713072943090" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="17599" width="200" height="200"><path d="M449.408 896H192a64 64 0 0 1-64-64V192a64 64 0 0 1 64-64h576a64 64 0 0 1 64 64v219.008c23.04 10.112 44.096 23.424 64 38.464V192A128 128 0 0 0 768 64H192A128 128 0 0 0 64 192v640a128 128 0 0 0 128 128h321.728a322.432 322.432 0 0 1-64.32-64z" fill="" p-id="17600"></path><path d="M704 448a256 256 0 1 0 0 512 256 256 0 0 0 0-512z m0 447.872a191.872 191.872 0 1 1 0-383.808 191.872 191.872 0 0 1 0 383.808z" fill="" p-id="17601"></path><path d="M800 672h-64v-32a32 32 0 0 0-64 0v64a32 32 0 0 0 32 32h96a32 32 0 0 0 0-64zM438.848 265.216a31.808 31.808 0 0 0-44.992 0L287.232 371.84l-42.688-42.688a30.72 30.72 0 1 0-43.456 43.456l59.584 59.584c1.344 2.304 2.432 4.672 4.416 6.656 6.656 6.592 15.36 9.408 23.936 9.088a30.848 30.848 0 0 0 21.824-9.024c0.704-0.704 1.024-1.6 1.6-2.432l126.4-126.336a31.744 31.744 0 0 0 0-44.928zM544 320a32 32 0 0 0 0 64h192a32 32 0 0 0 0-64H544zM393.856 489.216L287.232 595.84l-42.688-42.688a30.72 30.72 0 1 0-43.456 43.456l59.584 59.584c1.344 2.304 2.432 4.672 4.416 6.656 6.656 6.592 15.36 9.408 23.936 9.088a30.848 30.848 0 0 0 21.824-9.024c0.704-0.704 1.024-1.6 1.6-2.432l126.4-126.336a31.872 31.872 0 0 0-44.992-44.928z" fill="" p-id="17602"></path></svg>
      </el-icon>
      开发安排</div>
    <div style="font-size: 15px;color: gray">这里查看开发安排</div>
    <el-divider style="margin: 10px 0"/>

    <div style="margin-bottom: 20px">
      <el-checkbox-group v-model="checkedNodes">
        <el-checkbox v-for="node in locations" :key="node" :label="node.name" border>
          <span style="font-size: 13px;margin-left: 10px">{{node.desc}}</span>
        </el-checkbox>
      </el-checkbox-group>
    </div>

    <el-timeline style="max-width: 100%">
      <el-timeline-item style="width: 70%" v-for="item in time" :key="item" :timestamp="item" placement="top">
        <el-card class="taskCard">
          <span style="display: flex; align-items: center; width: 40%">
            <h4>Update Github template</h4>
            <el-tag style="margin-left: 40px">开发</el-tag>
          </span>
          <p>Tom committed {{item}} 20:46</p>
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<style scoped>
.taskCard {
  background-image: linear-gradient(to right, rgba(212, 234, 247, 0.6), #d6d6d6);
}
</style>