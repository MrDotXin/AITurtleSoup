<template>
  <header class="global-header">
    <a-row align="middle" justify="space-between">
      <a-col>
        <h1 class="logo">AI海龟汤</h1>
      </a-col>
      <a-col>
        <div class="user-info">
          <a-avatar
            :src="userStore.loginUser?.userAvatar"
            :style="!userStore.loginUser?.userAvatar ? avatarStyle : null"
          >
            {{ avatarFallback }}
          </a-avatar>
          <span class="username">{{ userStore.loginUser?.userName }}</span>
        </div>
      </a-col>
    </a-row>
  </header>
</template>

<script setup lang="ts">
import { UserStore } from '../stores/user'
import { computed } from 'vue'

const userStore = UserStore()
const avatarFallback = computed(() =>
  userStore.loginUser?.userName?.slice(0, 1).toUpperCase() || 'U'
)

const avatarStyle = {
  backgroundColor: '#00ff88',
  color: '#001f0d',
  fontWeight: 'bold'
}
</script>

<style scoped>
.logo {
  color: #00ff88;
  margin: 0;
  font-size: 1.5em;
  text-shadow: 0 0 10px rgba(0, 255, 136, 0.5);
}

.user-info {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 10px 0;
}

.username {
  color: rgba(0, 255, 136, 0.9);
  font-weight: 500;
}

.global-header {
  width: 100%;
}
</style>
