<script setup>

import {Delete, Edit} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";
import {get, post} from "@/net";
import {reactive, ref} from "vue";

const show = ref(false)
const emit = defineEmits(['updateWarnRule'])
const registerTable = reactive({
  id: '',
  name: '',
  description: '',
  clientId: '',
  cpuWarn: 80,
  memoryWarn: 80,
})
defineProps({
  warnRule: Object
})

function init(warnRule) {
  show.value = true
  registerTable.id = warnRule.id
  registerTable.name = warnRule.name
  registerTable.clientId = warnRule.clientId
  registerTable.description = warnRule.description
  registerTable.cpuWarn = warnRule.cpuWarn
  registerTable.memoryWarn = warnRule.memoryWarn
}

function deleteWarnRule(id) {
  get(`/api/warnRules/deleteWarnRule?id=${id}`, () => {
    ElMessage.success('预警规则删除成功')
    emit('update-warn-rule')
  })
}

function updateRule() {
  post('/api/warnRules/updateWarnRule', registerTable, () => {
    ElMessage.success('预警规则修改成功')
    show.value = false
    emit('update-warn-rule')
  })
}
</script>
<template>
  <div class="card">
    <div style="margin-left: 20px">
      <span style="display: flex; justify-content: space-between; font-weight: bold; font-size: 20px">
        <span>{{ warnRule.clientName }}</span>
        <span style="margin-left: 20px;font-size: 15px;font-weight: normal;color: #04c104">
          <el-tag>在线</el-tag>
        </span>
      </span>
      <div style="font-size: 15px;color: gray">
        <div>
          <div>
            CPU
            <i class="fa-solid fa-microchip"></i>: {{ warnRule.cpuWarn }}
          </div>
          <div>
            Memory
            <i class="fa-solid fa-memory"></i>：{{ warnRule.memoryWarn }}
          </div>
          <div>
            desc: {{ warnRule.description }}
          </div>
        </div>
      </div>
    </div>
    <div class="button-container">
      <el-button style="float: right;margin-right: 10px" :icon="Delete" color="#f5f5e9"
                 @click="deleteWarnRule(warnRule.id)">删除
      </el-button>
      <el-button style="float: right;margin-right: 10px;margin-top: 10px" :icon="Edit" color="#f5f5e9"
                 @click="init(warnRule)">修改预警阈值
      </el-button>
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
  height: 123px;
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