<script setup lang="ts">
import { UserStore } from '@/stores/user'

const userStore = UserStore()
// 初始化时获取用户信息
userStore.fetchCurrentUser()
</script>

<template>
  <router-view />
</template>
