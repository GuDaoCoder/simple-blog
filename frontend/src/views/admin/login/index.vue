<template>
  <div class="flex h-screen flex-col items-center overflow-auto bg-white pt-[30vh]">
    <blog-logo :height="60" />
    <div class="mt-4 w-[300px]">
      <a-form :model="loginForm" @submit="handleLogin">
        <a-space direction="vertical" size="medium">
          <a-input
            v-model="loginForm.username"
            field="username"
            placeholder="请输入用户名"
            allow-clear
            size="large"
          >
            <template #prefix>
              <icon-user />
            </template>
          </a-input>
          <a-input-password
            v-model="loginForm.password"
            field="password"
            placeholder="请输入密码"
            allow-clear
            size="large"
          >
            <template #prefix>
              <icon-lock />
            </template>
          </a-input-password>
          <a-button :loading="loading" class="w-full" html-type="submit" type="primary" size="large"
            >登陆
          </a-button>
        </a-space>
      </a-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { adminLogin } from '@/api/auth/index'
import { Notification } from '@arco-design/web-vue'
import { setToken } from '@/utils/auth'

const initLoginForm = (): ApiAuth.LoginForm => {
  return {
    username: 'admin',
    password: '123456'
  }
}
const loginForm = ref<ApiAuth.LoginForm>(initLoginForm())

const router = useRouter()
const loading = ref(false)
const handleLogin = async () => {
  if (!loginForm.value.username) {
    Notification.warning('请输入用户名')
    return
  }
  if (!loginForm.value.password) {
    Notification.warning('请输入密码')
    return
  }
  if (loading.value) return
  loading.value = true
  await adminLogin(loginForm.value)
    .then((res) => {
      setToken(res.token)
      router.push('/admin/home')
      Notification.success('欢迎进入博客管理系统')
    })
    .finally(() => (loading.value = false))
}
</script>

<style scoped></style>
