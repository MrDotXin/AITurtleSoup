<!-- src/views/auth/RegisterView.vue -->
<template>
  <div class="auth-container">
    <div class="background-glow"></div>
    <a-card class="auth-card" :bordered="false">
      <h2 class="brand-text">注册账号</h2>
      <a-form
        class="auth-form"
        :model="formState"
        @finish="handleRegister"
      >
        <a-form-item
          name="userAccount"
          :rules="[{ required: true, message: '请输入账号!' }]"
        >
          <a-input
            v-model:value="formState.userAccount"
            placeholder="账号"
            size="large"
            style="background: transparent"
          >
            <template #prefix>
              <UserOutlined />
            </template>
          </a-input>
        </a-form-item>

        <a-form-item
          name="userPassword"
          :rules="[{ required: true, message: '请输入密码!' }]"
        >
          <a-input-password
            v-model:value="formState.userPassword"
            placeholder="密码"
            size="large"
          >
            <template #prefix>
              <LockOutlined />
            </template>
          </a-input-password>
        </a-form-item>

        <a-form-item
          name="checkPassword"
          :rules="[
            { required: true, message: '请确认密码!' },
            { validator: validatePass },
          ]"
        >
          <a-input-password
            v-model:value="formState.checkPassword"
            placeholder="确认密码"
            size="large"
          >
            <template #prefix>
              <LockOutlined />
            </template>
          </a-input-password>
        </a-form-item>

        <a-form-item>
          <a-button
            type="primary"
            html-type="submit"
            size="large"
            block
            :loading="loading"
          >
            注册
          </a-button>
        </a-form-item>

        <div class="auth-extra">
          已有账号？
          <router-link to="/login">立即登录</router-link>
        </div>
      </a-form>
    </a-card>
  </div>
</template>

<script setup lang="ts">
import { UserOutlined, LockOutlined } from '@ant-design/icons-vue';
import { message } from 'ant-design-vue';
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { UserStore } from '@/stores/user';

interface FormState {
  userAccount: string;
  userPassword: string;
  checkPassword: string;
}

const formState = reactive<FormState>({
  userAccount: '',
  userPassword: '',
  checkPassword: '',
});

const userStore = UserStore();
const router = useRouter();
const loading = ref(false);

const validatePass = async (_rule: any, value: string) => {
  if (value !== formState.userPassword) {
    throw new Error('两次输入的密码不一致!');
  }
};

const handleRegister = async () => {
  loading.value = true;
  try {
    const res = await userStore.userRegister(formState);
    if (res?.code === 0) {
      message.success('注册成功');
      router.push('/');
    } else {
      message.error(res?.message || '注册失败');
    }
  } finally {
    loading.value = false;
  }
};
</script>

<!-- 使用与登录页面相同的样式 -->
<style scoped>
/* 统一所有输入框的基础样式 */
:deep(.auth-form .ant-input),
:deep(.auth-form .ant-input-password .ant-input) {
  background: rgba(40, 40, 40, 0.9) !important;
  border: 2px solid rgba(0, 255, 128, 0.4) !important;
  border-radius: 8px;
  color: #fff !important;
  padding: 14px 20px;
  font-size: 16px;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

/* 统一placeholder样式 */
:deep(.auth-form .ant-input)::placeholder,
:deep(.auth-form .ant-input-password .ant-input)::placeholder {
  color: rgba(255, 255, 255, 0.6) !important;
  font-weight: 300;
}

/* 统一悬停状态 */
:deep(.auth-form .ant-input:hover),
:deep(.auth-form .ant-input-password:hover .ant-input) {
  border-color: #00ff80 !important;
  background: rgba(50, 50, 50, 0.9) !important;
}

/* 统一聚焦状态 */
:deep(.auth-form .ant-input:focus),
:deep(.ant-input-focused),
:deep(.auth-form .ant-input-password:focus .ant-input) {
  border-color: #00ff80 !important;
  box-shadow: 0 0 0 3px rgba(0, 255, 128, 0.2) !important;
  background: rgba(60, 60, 60, 0.9) !important;
}

/* 统一图标样式 */
:deep(.ant-input-prefix),
:deep(.ant-input-password .ant-input-suffix) {
  color: rgba(255, 255, 255, 0.6) !important;
  transition: color 0.3s ease;
}

:deep(.ant-input-prefix:hover),
:deep(.ant-input-password:hover .ant-input-suffix) {
  color: #00ff80 !important;
}

/* 调整密码框内部结构 */
:deep(.ant-input-password) {
  padding: 0;
  background: transparent !important;
}

/* 确保密码输入框的内层input继承样式 */
:deep(.ant-input-password .ant-input) {
  height: 100%;
  background: inherit;
  border: none !important;
  box-shadow: none !important;
}

/* 确保输入文字清晰 */
:deep(.auth-form .ant-input),
:deep(.auth-form .ant-input-password input) {
  color: #fff !important;
  font-weight: 400;
  letter-spacing: 0.5px;
}

/* 新版样式 */
.auth-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  width: 100vw;
  background: linear-gradient(135deg, #0a0a0a, #1a1a1a);
  position: relative;
  overflow: hidden;
}

.background-glow {
  position: absolute;
  width: 200vw;
  height: 200vh;
  background: radial-gradient(circle at 50% 50%,
    rgba(0, 255, 128, 0.1) 0%,
    rgba(0, 0, 0, 0) 60%);
  animation: glow-pulse 8s infinite;
}

@keyframes glow-pulse {
  0%, 100% { transform: scale(1); opacity: 0.3; }
  50% { transform: scale(1.2); opacity: 0.5; }
}

.auth-card {
  width: 90%;
  max-width: 600px;
  background: rgba(20, 20, 20, 0.95);
  border-radius: 12px;
  backdrop-filter: blur(10px);
  border: 1px solid rgba(0, 255, 128, 0.2);
  box-shadow: 0 0 40px rgba(0, 255, 128, 0.1);
  transition: all 0.3s ease;
  z-index: 1;
}

.auth-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 50px rgba(0, 255, 128, 0.2);
}

.brand-text {
  color: #fff;
  text-align: center;
  margin: 2rem 0;
  font-size: 2.5rem;
  text-shadow: 0 0 15px rgba(0, 255, 128, 0.5);
}

.logo-text {
  display: inline-block;
  animation: text-glow 2s ease-in-out infinite alternate;
}

@keyframes text-glow {
  from { text-shadow: 0 0 10px rgba(0, 255, 128, 0.3); }
  to { text-shadow: 0 0 20px rgba(0, 255, 128, 0.6); }
}

:deep(.auth-form .ant-input),
:deep(.auth-form .ant-input-password) {
  background: rgba(40, 40, 40, 0.9);
  border: 1px solid rgba(0, 255, 128, 0.3);
  color: #fff;
  padding: 12px 20px;
  transition: all 0.3s ease;
}

:deep(.auth-form .ant-input:hover),
:deep(.auth-form .ant-input:focus),
:deep(.ant-input-password:hover) {
  border-color: #00ff80;
  box-shadow: 0 0 10px rgba(0, 255, 128, 0.2);
}

:deep(.auth-form .ant-input-password-icon) {
  color: rgba(255, 255, 255, 0.6);
  transition: color 0.3s ease;
}

:deep(.auth-form .ant-input-password-icon:hover) {
  color: #00ff80;
}

.action-btn {
  background: linear-gradient(45deg, #00ff80, #00cc66);
  border: none;
  height: 48px;
  font-weight: bold;
  letter-spacing: 1px;
  transition: all 0.3s ease;
}

.action-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(0, 255, 128, 0.3);
}

.auth-extra {
  color: rgba(255, 255, 255, 0.8);
  text-align: center;
  font-size: 0.95rem;
}

.link-effect {
  color: #00ff80;
  position: relative;
  padding-bottom: 2px;
}

.link-effect::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  width: 0;
  height: 1px;
  background: #00ff80;
  transition: width 0.3s ease;
}

.link-effect:hover::after {
  width: 100%;
}

/* 入场动画 */
.card-slide-enter-active {
  transition: all 0.6s cubic-bezier(0.4, 0, 0.2, 1);
}
.card-slide-enter-from {
  opacity: 0;
  transform: translateY(50px);
}

.auth-card {
  max-width: 650px;
}
</style>
