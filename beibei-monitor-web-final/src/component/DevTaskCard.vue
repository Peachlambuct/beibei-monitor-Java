<script setup>

import {ElMessage} from "element-plus";
import {post} from "@/net";

function formatDate(value) {
  if (value) {
    let date = new Date(value);
    return date.getFullYear() + '-' + (date.getMonth() + 1).toString().padStart(2, '0') + '-' + date.getDate().toString().padStart(2, '0');
  }
}
const prop = defineProps({
  data: Object
})

const emit = defineEmits(['flash'])

function finishTask() {
  post('/api/task/updateSubtask', {subtaskId: prop.data.id, status: 2}, () => {
    ElMessage.success('提交成功，恭喜你完成了此任务')
    emit('flash')
  })
}
</script>

<template>
  <div class="card">
    <div style="display: flex;justify-content: space-between;margin-top: 10px">
      <div style="margin-left: 20px">
        <div style="font-weight: bold;font-size: 20px">{{ data.name }}</div>
      </div>
      <div class="button-container">
        <el-button style="float: right;margin-right: 10px"
                   @click="finishTask">
          <template #icon>
            <el-icon style="font-size: 21px">
              <svg t="1713368685810" class="icon" viewBox="0 0 1063 1024" version="1.1"
                   xmlns="http://www.w3.org/2000/svg" p-id="2533" width="200" height="200">
                <path
                    d="M356.848485 356.848485m-356.848485 0a356.848485 356.848485 0 1 0 713.69697 0 356.848485 356.848485 0 1 0-713.69697 0Z"
                    fill="#8CF6FB" p-id="2534"></path>
                <path
                    d="M1033.309091 479.418182l-152.048485 144.290909c-23.272727 21.721212-34.133333 52.751515-27.927273 83.781818l38.787879 206.351515c9.309091 51.2-24.824242 99.29697-74.472727 108.606061-4.654545 1.551515-10.860606 1.551515-15.515152 1.551515-15.515152 0-31.030303-4.654545-44.993939-12.412121l-178.424242-99.29697c-27.927273-15.515152-62.060606-15.515152-89.987879 0L310.30303 1011.587879c-13.963636 7.757576-29.478788 12.412121-44.993939 12.412121-20.169697 0-38.787879-6.206061-54.30303-18.618182-29.478788-20.169697-43.442424-57.406061-37.236364-93.090909l38.787879-206.351515c6.206061-31.030303-4.654545-63.612121-27.927273-83.781818l-152.048485-144.290909c-37.236364-35.684848-38.787879-94.642424-3.10303-131.878788 13.963636-15.515152 34.133333-24.824242 55.854545-27.927273l204.8-26.375758c32.581818-3.10303 58.957576-23.272727 72.921212-52.751515L449.939394 54.30303c21.721212-46.545455 76.024242-66.715152 122.569697-44.993939 20.169697 9.309091 35.684848 24.824242 44.993939 44.993939l86.884849 187.733334c12.412121 29.478788 40.339394 49.648485 72.921212 52.751515l204.8 26.375757c51.2 6.206061 86.884848 52.751515 80.678788 103.951516-4.654545 20.169697-13.963636 38.787879-29.478788 54.30303z m-49.648485-72.921212c-1.551515-6.206061-6.206061-10.860606-12.412121-10.860606l-204.8-26.375758c-58.957576-6.206061-108.606061-43.442424-131.878788-97.745454L547.684848 86.884848c-3.10303-7.757576-12.412121-10.860606-20.169696-7.757575-3.10303 1.551515-6.206061 4.654545-7.757576 7.757575l-86.884849 187.733334c-24.824242 52.751515-74.472727 89.987879-133.430303 97.745454L96.193939 395.636364c-7.757576 1.551515-13.963636 9.309091-13.963636 17.066666 0 3.10303 1.551515 6.206061 4.654545 9.309091l152.048485 144.290909c41.890909 38.787879 60.509091 97.745455 49.648485 155.151515l-38.787879 206.351516c-1.551515 6.206061 1.551515 12.412121 6.206061 15.515151 3.10303 1.551515 6.206061 3.10303 9.309091 3.10303 3.10303 0 4.654545-1.551515 7.757576-3.10303L449.939394 845.575758c51.2-29.478788 114.812121-29.478788 166.012121 0l178.424243 99.296969c3.10303 1.551515 4.654545 1.551515 7.757575 3.103031 3.10303 0 6.206061-1.551515 9.309091-3.103031 4.654545-3.10303 7.757576-9.309091 6.206061-15.515151l-38.787879-206.351515c-10.860606-57.406061 7.757576-114.812121 49.648485-155.151516l152.048485-144.290909c4.654545-4.654545 6.206061-10.860606 3.10303-17.066666z"
                    fill="#3C2DCB" p-id="2535"></path>
                <path
                    d="M541.478788 704.387879c-15.515152 15.515152-40.339394 15.515152-54.30303 0l-121.018182-121.018182c-13.963636-15.515152-13.963636-40.339394 1.551515-54.30303 15.515152-13.963636 37.236364-13.963636 52.751515 0l93.090909 93.090909 181.527273-181.527273c15.515152-13.963636 40.339394-13.963636 54.30303 1.551515 13.963636 15.515152 13.963636 37.236364 0 52.751515L541.478788 704.387879z"
                    fill="#D098FF" p-id="2536"></path>
              </svg>
            </el-icon>
          </template>
        </el-button>
      </div>
    </div>
    <el-divider style="margin: 10px 0"/>
    <el-scrollbar max-height="180">
      <div class="desc" style="margin-left: 20px">
        <div>任务描述：{{ data.description }}</div>
        <div>任务小组成员：{{ data.principals }}</div>
        <div>任务开始时间：{{ data.startTime }}</div>
        <div>任务结束时间：{{ data.endTime }}</div>
        <div>任务状态：{{ data.status === 0 ? '未完成' : '已完成' }}</div>
        <div>任务相关服务器：{{ data.aboutClients }}</div>
        <div>最后更新时间：{{ formatDate(data.updateTime) }}</div>
        <div>最后更新成员：{{ data.updateUserName }}</div>
      </div>
    </el-scrollbar>
  </div>

</template>

<style scoped>
.card {
  background-image: linear-gradient(to right, #efeeee, #f8f8f8);
  height: 250px;
  width: 45%;
  border-radius: 10px;
  margin: 16px;
  min-width: 300px;
}

.button-container {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  margin-right: 10px;
}

.desc {
  div {
    color: #5c5c5c;
    font-size: 15px;
  }

  max-height: 200px;
  height: 200px;
}
</style>