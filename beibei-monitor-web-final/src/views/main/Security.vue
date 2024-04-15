<script setup>
import {reactive, ref} from "vue";
import {Delete, Lock, Plus, Refresh, Switch} from "@element-plus/icons-vue";
import {get, logout, post} from "@/net";
import {ElMessage} from "element-plus";
import router from "@/router";
import CreateSubAccount from "@/component/CreateSubAccount.vue";
import {useStore} from "@/store";

const store = useStore()

const formRef = ref()
const valid = ref(false)
const onValidate = (prop, isValid) => valid.value = isValid

const form = reactive({
  password: '',
  new_password: '',
  new_password_repeat: '',
})

const validatePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== form.new_password) {
    callback(new Error("两次输入的密码不一致"))
  } else {
    callback()
  }
}

const emailForm = reactive({
  email: store.user.email,
  code: ''
})

const coldTime = ref(0)
const isEmailValid = ref(true)

const onEmailValidate = (prop, isValid) => {
  if(prop === 'email')
    isEmailValid.value = isValid
}

const validateEmail = () => {
  coldTime.value = 60
  let handle;
  get(`/api/auth/ask-code?email=${emailForm.email}&type=modify`, () => {
    ElMessage.success(`验证码已发送到邮箱: ${emailForm.email}，请注意查收`)
    handle = setInterval(() => {
      coldTime.value--
      if(coldTime.value === 0) {
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

const rules = {
  password: [
    { required: true, message: '请输入原来的密码', trigger: 'blur' },
  ],
  new_password: [
    { required: true, message: '请输入新的密码', trigger: 'blur' },
    { min: 6, max: 16, message: '密码的长度必须在6-16个字符之间', trigger: ['blur'] }
  ],
  new_password_repeat: [
    { required: true, message: '请重复输入新的密码', trigger: 'blur' },
    { validator: validatePassword, trigger: ['blur', 'change'] },
  ],
  email: [
    { required: true, message: '请输入邮件地址', trigger: 'blur' },
    {type: 'email', message: '请输入合法的电子邮件地址', trigger: ['blur', 'change']}
  ]
}

function resetPassword() {
  formRef.value.validate(isValid => {
    if(isValid) {
      post('/api/user/change-password', form, () => {
        ElMessage.success('密码修改成功，请重新登录!')
        logout(() => router.push('/'))
      })
    }
  })
}

const simpleList = ref([])
if(store.isAdmin) {
  get('/api/monitor/simple-list', list => {
    simpleList.value = list
    initSubAccounts()
  })
}

const accounts = ref([])
const initSubAccounts = () =>
    get('/api/user/sub/list', list => accounts.value = list)

const createAccount = ref(false)

function deleteAccount(id) {
  get(`/api/user/sub/delete?uid=${id}`, () => {
    ElMessage.success('子账户删除成功')
    initSubAccounts()
  })
}
</script>

<template>
  <div style="margin: 0 50px">
    <div style="font-size: 22px;font-weight: bold">
      <el-icon style="height: 100%">
        <svg t="1713072623791" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4878" width="200" height="200"><path d="M867.584 160.192c-149.632-16.928-262.208-57.408-334.592-120.352l-19.04-16.544-20.544 14.656C379.968 118.944 267.776 160 160 160H128v448c0 137.344 121.088 261.92 370.208 380.864l13.088 6.24 13.344-5.728C771.072 883.52 896 755.232 896 608V163.424l-28.416-3.232zM832 608c0 116.8-107.392 223.36-319.328 316.8C299.872 821.024 192 714.464 192 608V222.976c104.672-6.784 211.584-46.688 318.496-118.944C587.232 162.528 695.168 201.536 832 220.256V608z" p-id="4879"></path><path d="M359.776 468.672a32 32 0 1 0-47.968 42.4l121.792 137.824c12.608 14.24 30.176 21.568 47.904 21.568a64.384 64.384 0 0 0 49.696-23.52l197.6-242.72a32 32 0 0 0-49.632-40.416l-197.6 242.688-121.792-137.824z" p-id="4880"></path></svg>
      </el-icon>
      账户安全管理</div>
    <div style="font-size: 15px;color: grey">查看安全信息，修改邮箱或密码</div>

    <el-divider style="margin: 10px 0"/>

    <div style="display: flex;gap: 10px">
      <div style="flex: 50%;justify-content: center;align-items: center;display: flex;height: 100%;flex-direction: column;">
        <div class="info-card">
          <div class="title"><i class="fa-solid fa-lock"></i> 修改密码</div>
          <el-divider style="margin: 10px 0"/>
          <el-form @validate="onValidate" :model="form" :rules="rules"
                   ref="formRef" style="margin: 20px" label-width="100">
            <el-form-item label="当前密码" prop="password">
              <el-input type="password" v-model="form.password"
                        :prefix-icon="Lock" placeholder="当前密码" maxlength="16"/>
            </el-form-item>
            <el-form-item label="新密码" prop="new_password">
              <el-input type="password" v-model="form.new_password"
                        :prefix-icon="Lock" placeholder="新密码" maxlength="16"/>
            </el-form-item>
            <el-form-item label="重复新密码" prop="new_password_repeat">
              <el-input type="password" v-model="form.new_password_repeat"
                        :prefix-icon="Lock" placeholder="重复新密码" maxlength="16"/>
            </el-form-item>
            <div style="text-align: center">
              <el-button :icon="Switch" @click="resetPassword"
                         type="success" :disabled="!valid">立即重置密码</el-button>
            </div>
          </el-form>
        </div>
        <div class="info-card" style="margin-top: 10px">
          <div class="title"><i class="fa-regular fa-envelope"></i> 电子邮件设置</div>
          <el-divider style="margin: 10px 0"/>
          <el-form :model="emailForm" label-position="top" :rules="rules"
                   ref="emailFormRef" @validate="onEmailValidate" style="margin: 0 10px 10px 10px">
            <el-form-item label="电子邮件" prop="email">
              <el-input v-model="emailForm.email"/>
            </el-form-item>
            <el-form-item>
              <el-row style="width: 100%" :gutter="10">
                <el-col :span="18">
                  <el-input placeholder="请获取验证码" v-model="emailForm.code"/>
                </el-col>
                <el-col :span="6">
                  <el-button type="success" @click="validateEmail" style="width: 100%;"
                             :disabled="!isEmailValid || coldTime > 0">
                    {{coldTime > 0 ? '请稍后 ' + coldTime + ' 秒' : '获取验证码'}}
                  </el-button>
                </el-col>
              </el-row>
            </el-form-item>
            <div>
              <el-button @click="modifyEmail" :disabled="!emailForm.email"
                         :icon="Refresh" type="success">保存电子邮件</el-button>
            </div>
          </el-form>
        </div>
      </div>
    </div>
  </div>



</template>

<style>
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
