<script setup>

import {Plus} from "@element-plus/icons-vue";
import WarnCard from "@/component/WarnCard.vue";
import {reactive, ref} from "vue";
import {get, post} from "@/net";
import {ElMessage} from "element-plus";

const addWarn = ref(false)
const registerTable = reactive({
  id: '',
  name: '',
  description: '',
  clientId: '',
  cpuWarn: 80,
  memoryWarn: 80,
})
const serverList = ref([])

function clearTable() {
  registerTable.id = ''
  registerTable.name = ''
  registerTable.description = ''
  registerTable.clientId = ''
  registerTable.cpuWarn = 80
  registerTable.memoryWarn = 80
}

const warnList = ref([])
function getWarnList() {
  get('/api/warnRules/list', list => {
    warnList.value = list
  })
}

function getServerNameList() {
  get('/api/monitor/list', list => {
    serverList.length = 0
    list.forEach(item => {
      serverList.value.push({label: item.name, value: item.id})
    })
    console.info(serverList.value)
  })
}

getWarnList()
getServerNameList()
function registerWarnRule() {
  post('/api/warnRules/addWarnRule', registerTable, () => {
    ElMessage.success('预警规则添加成功')
    getWarnList()
    addWarn.value = false
    clearTable()
  })
}
</script>

<template>
  <div style="margin: 0 50px">
    <div style="display: flex;justify-content: space-between;align-items: end">
      <div>
        <div style="font-size: 22px;font-weight: bold">
          <el-icon style="height: 100%">
            <svg t="1713072732062" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                 p-id="9286" width="200" height="200">
              <path
                  d="M362.666667 885.333333H170.666667A53.333333 53.333333 0 0 1 117.333333 832V170.666667A53.333333 53.333333 0 0 1 170.666667 117.333333h640A53.333333 53.333333 0 0 1 864 170.666667v181.333333a32 32 0 0 0 64 0V170.666667A117.333333 117.333333 0 0 0 810.666667 53.333333H170.666667A117.333333 117.333333 0 0 0 53.333333 170.666667v661.333333A117.333333 117.333333 0 0 0 170.666667 949.333333h192a32 32 0 0 0 0-64z"
                  fill="#2c2c2c" p-id="9287"></path>
              <path
                  d="M544 436.266667a32 32 0 0 0-10.666667 43.946666l20.053334 32.853334a32 32 0 0 0 54.613333-33.28L587.946667 448a32 32 0 0 0-43.946667-11.733333zM896 436.266667a32 32 0 0 0-43.946667 10.666666L832 479.786667a32 32 0 1 0 54.613333 33.28l20.053334-32.853334a32 32 0 0 0-10.666667-43.946666zM912 583.04a32 32 0 0 0 44.373333 8.96l32-21.333333a32 32 0 0 0-35.413333-53.333334l-32 21.333334a32 32 0 0 0-8.96 44.373333zM692.266667 421.76l-2.346667 38.4a32 32 0 1 0 64 4.266667l2.56-38.4a32 32 0 1 0-64-4.266667zM970.666667 885.333333H896v-192a181.333333 181.333333 0 0 0-362.666667 0v192h-64a32 32 0 0 0 0 64h501.333334a32 32 0 0 0 0-64zM832 874.666667H597.333333v-181.333334a117.333333 117.333333 0 0 1 234.666667 0z"
                  fill="#2c2c2c" p-id="9288"></path>
              <path
                  d="M714.666667 597.333333a32 32 0 0 0-32 32V725.333333a32 32 0 0 0 64 0v-96a32 32 0 0 0-32-32zM353.28 265.386667a31.786667 31.786667 0 0 0-45.226667 0l-117.333333 117.333333a31.786667 31.786667 0 0 0 0 45.226667l117.333333 117.333333a32 32 0 1 0 45.226667-45.226667L258.56 405.333333l94.72-94.72a31.786667 31.786667 0 0 0 0-45.226666zM506.666667 583.04a32 32 0 0 0-8.96-44.373333l-32-21.333334a32 32 0 1 0-35.413334 53.333334l32 21.333333a32 32 0 0 0 44.373334-8.96z"
                  fill="#2c2c2c" p-id="9289"></path>
              <path d="M714.666667 821.333333m-32 0a32 32 0 1 0 64 0 32 32 0 1 0-64 0Z" fill="#2c2c2c"
                    p-id="9290"></path>
            </svg>
          </el-icon>
          预警规则管理
        </div>
        <div style="font-size: 15px;color: gray">这里可以管理你预先设置好的预警规则</div>
      </div>
      <div>
        <el-button color="#b6ccd8" :icon="Plus" type="primary"
                   @click="addWarn = true">添加新的预警规则
        </el-button>
      </div>
    </div>
    <el-divider style="margin: 10px 0"/>
    <el-dialog v-model="addWarn" title="添加新的预警规则" width="800" style="height: 400px;border-radius: 15px;">
      <div style="display: flex; flex-direction: column; justify-content: space-between; height: 100%;">
        <div>
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
            <el-form-item label="预警规则触发服务器">
              <el-select v-model="registerTable.clientId" placeholder="请选择">
                <el-option v-for="item in serverList" :label="item.label" :value="item.value"/>
              </el-select>
            </el-form-item>
          </el-form>
        </div>
        <div style="display: flex; justify-content: flex-end; align-items: flex-end; width: 100%;">
          <el-button type="success" @click="registerWarnRule">保存</el-button>
          <el-button type="danger" @click="addWarn = false">取消</el-button>
        </div>
      </div>
    </el-dialog>

    <div style="display: flex; flex-direction: row; flex-wrap: wrap;">
      <WarnCard v-for="item in warnList" :warn-rule="item" @update-warn-rule="getWarnList"/>
    </div>
  </div>
</template>

<style scoped>

</style>