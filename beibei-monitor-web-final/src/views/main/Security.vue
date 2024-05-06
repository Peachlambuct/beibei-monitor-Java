<script setup>
import {reactive, ref} from "vue";
import {Delete, Lock, Plus, Refresh, Switch} from "@element-plus/icons-vue";
import {get, logout, post} from "@/net";
import {ElMessage} from "element-plus";
import router from "@/router";
import CreateSubAccount from "@/component/CreateSubAccount.vue";
import {useStore} from "@/store";

const store = useStore()

const form = reactive({
  password: '',
  new_password: '',
  new_password_repeat: '',
})

const emailForm = reactive({
  email: store.user.email,
  code: ''
})

const coldTime = ref(0)
const isEmailValid = ref(true)

const validateEmail = () => {
  coldTime.value = 60
  let handle;
  get(`/api/auth/ask-code?email=${emailForm.email}&type=modify`, () => {
    ElMessage.success(`验证码已发送到邮箱: ${emailForm.email}，请注意查收`)
    handle = setInterval(() => {
      coldTime.value--
      if (coldTime.value === 0) {
        clearInterval(handle)
      }
    }, 1000)
  }, (message) => {
    ElMessage.warning(message)
    coldTime.value = 0
  })
}

function modifyEmail() {
  post('/api/user/modify-email', emailForm, () => {
    ElMessage.success('邮件修改成功')
    logout(() => router.push('/'))
  })
}

function resetPassword() {
  post('/api/user/change-password', {password: form.password, new_password: form.new_password}, () => {
    ElMessage.success('密码修改成功，请重新登录!')
    logout(() => router.push('/'))
  })
}

const rules = {
  password: [
    val => !!val || '请输入当前密码',
    val => val.length >= 6 || '密码长度不能低于6位'
  ],
  new_password: [
    val => !!val || '请输入新密码',
    val => val.length >= 6 || '密码长度不能低于6位'
  ],
  new_password_repeat: [
    val => val === form.new_password || '两次输入的密码不一致',
    val => val.length >= 6 || '密码长度不能低于6位'
  ],
};

const simpleList = ref([])

if (store.isAdmin) {
  get('/api/monitor/simple-list', list => {
    simpleList.value = list
    initSubAccounts()
  })
}

const accounts = ref([])
const initSubAccounts = () =>
    get('/api/user/sub/list', list => accounts.value = list)

function validatePassword(rule, value, callback) {
  if (value !== form.new_password) {
    callback(new Error('两次输入的密码不一致'));
  } else {
    callback();
  }
}
</script>

<template>
  <div style="margin: 0 50px">
    <div style="font-size: 22px;font-weight: bold">
      <el-icon style="height: 100%">
        <svg t="1713072623791" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
             p-id="4878" width="200" height="200">
          <path
              d="M867.584 160.192c-149.632-16.928-262.208-57.408-334.592-120.352l-19.04-16.544-20.544 14.656C379.968 118.944 267.776 160 160 160H128v448c0 137.344 121.088 261.92 370.208 380.864l13.088 6.24 13.344-5.728C771.072 883.52 896 755.232 896 608V163.424l-28.416-3.232zM832 608c0 116.8-107.392 223.36-319.328 316.8C299.872 821.024 192 714.464 192 608V222.976c104.672-6.784 211.584-46.688 318.496-118.944C587.232 162.528 695.168 201.536 832 220.256V608z"
              p-id="4879"></path>
          <path
              d="M359.776 468.672a32 32 0 1 0-47.968 42.4l121.792 137.824c12.608 14.24 30.176 21.568 47.904 21.568a64.384 64.384 0 0 0 49.696-23.52l197.6-242.72a32 32 0 0 0-49.632-40.416l-197.6 242.688-121.792-137.824z"
              p-id="4880"></path>
        </svg>
      </el-icon>
      账户安全管理
    </div>
    <div style="font-size: 15px;color: grey">查看安全信息，修改邮箱或密码</div>

    <el-divider style="margin: 10px 0"/>

    <div style="display: flex;gap: 10px">
      <div
          style="flex: 50%;justify-content: center;align-items: center;display: flex;height: 100%;flex-direction: column;">
        <div class="info-card">
          <div class="title"><i class="fa-solid fa-lock"></i> 修改密码</div>
          <el-divider style="margin: 10px 0"/>
          <div>
            <q-input style="margin-bottom: 6px" color="green" filled v-model="form.password" label="当前密码"
                     :rules="rules.password">
              <template v-slot:prepend>
                <q-icon name="vpn_key"/>
              </template>
            </q-input>

            <q-input style="margin-bottom: 6px" color="green" filled v-model="form.new_password" label="新密码"
                     :rules="rules.new_password">
              <template v-slot:prepend>
                <q-icon name="vpn_key"/>
              </template>
            </q-input>

            <q-input style="margin-bottom: 6px" color="green" filled v-model="form.new_password_repeat"
                     label="重复新密码" :rules="rules.new_password_repeat">
              <template v-slot:prepend>
                <q-icon name="vpn_key"/>
              </template>
            </q-input>

            <div style="text-align: center;">
              <q-btn color="secondary" @click="resetPassword">立即重置密码</q-btn>
            </div>
          </div>
        </div>
        <div class="info-card" style="margin-top: 10px">
          <div class="title"><i class="fa-regular fa-envelope"></i> 电子邮件设置</div>
          <el-divider style="margin: 10px 0"/>
          <q-form :model="emailForm" class="q-gutter-md">
            <q-input filled v-model="emailForm.email" label="电子邮件" :rules="[val => !!val || '请输入电子邮件']">
              <template v-slot:prepend>
                <q-icon name="email"/>
              </template>
            </q-input>

            <div class="row q-col-gutter-md">
              <q-input filled v-model="emailForm.code" class="col-8 custom-input" label="验证码"
                       :rules="[val => !!val || '请输入验证码']">
                <template v-slot:prepend>
                  <q-icon name="vpn_key"/>
                </template>
              </q-input>

              <q-btn class="col-3 custom-btn" color="secondary" @click="validateEmail"
                     :disabled="!isEmailValid || coldTime > 0" size="md">
                {{ coldTime > 0 ? '请稍后 ' + coldTime + ' 秒' : '获取验证码' }}
              </q-btn>
            </div>

            <q-btn style="text-align: center" color="secondary" @click="modifyEmail" :disabled="!emailForm.email"
                   icon="refresh">
              保存电子邮件
            </q-btn>
          </q-form>
        </div>
      </div>
    </div>
  </div>


</template>

<style>
.custom-input {
  margin-left: 16px;
}

.custom-btn {
  width: 80%;
  margin: auto;
}

.info-card {
  border-radius: 7px;
  padding: 15px 20px;
  background-color: rgba(239, 239, 239, 0.93);
  height: fit-content;
  width: 60%;
  margin: 20px 0;

  .title {
    font-size: 18px;
    font-weight: bold;
  }
}

:deep(.el-drawer) {
  margin: 10px;
  height: calc(100% - 20px);
  border-radius: 10px;
}

:deep(.el-drawer__body) {
  padding: 0;
}

:deep(el-form el-form--default el-form--label-right) {
  background-color: #fffefb;
}
</style>
