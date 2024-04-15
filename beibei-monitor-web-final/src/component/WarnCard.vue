<script setup>

import {Delete, Edit} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";
import {get, post} from "@/net";
import {reactive, ref} from "vue";

const show = ref(false)
const registerTable = reactive({
  name: '',
  description: '',
  clientId: '',
  cpuWarn: 80,
  memoryWarn: 80,
})
function deleteWarnRule(id) {
  get(`/api/warnRules/deleteWarnRule?id=${id}`, () => {
    ElMessage.success('预警规则删除成功')
  })
}

function updateRule() {
  post('/api/warnRules/updateWarnRule', registerTable, () => {
    ElMessage.success('预警规则修改成功')
  })
}
</script>

<template>
  <div class="card">
    <div style="margin-left: 20px">
      <div style="font-weight: bold;font-size: 20px">XXX服务器</div>
      <div>在线状态：在线</div>
      <div>CPU：80 | Memory：20</div>
    </div>
    <div class="button-container">
      <el-button style="float: right;margin-right: 10px" :icon="Delete" color="#f5f5e9"
                 @click="deleteWarnRule">删除</el-button>
      <el-button style="float: right;margin-right: 10px;margin-top: 10px" :icon="Edit" color="#f5f5e9"
                 @click="show = true">修改预警阈值</el-button>
    </div>
    <el-dialog v-model="show" title="修改告警规则">
      <el-form label-width="150px">
        <el-form-item label="预警规则名称">
          <el-input v-model="registerTable.name"/>
        </el-form-item>
        <el-form-item label="预警规则描述">
          <el-input v-model="registerTable.description"/>
        </el-form-item>
        <el-form-item label="CPU预警规则阈值">
          <el-input-number v-model="registerTable.cpuWarn" :min="80" :max="100"/>
        </el-form-item>
        <el-form-item label="内存预警规则阈值">
          <el-input-number v-model="registerTable.memoryWarn" :min="80" :max="100"/>
        </el-form-item>
      </el-form>
      <div style="display: flex; justify-content: flex-end; align-items: flex-end; width: 100%;">
        <el-button type="success" @click="updateRule">保存</el-button>
        <el-button type="danger" @click="show = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style scoped>
.card {
  background-image: linear-gradient(to right, rgba(212, 234, 247, 0.6), #d6d6d6);
  height: 110px;
  width: 45%;
  border-radius: 10px;
  margin: 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button-container {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  margin-right: 10px;
}
</style>