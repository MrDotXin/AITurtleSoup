<template>
  <a-layout class="turtle-soup-hall">

    <a-layout-content class="hall-content">
      <a-row justify="center">
        <a-col :xs="24" :sm="22" :md="20" :lg="18" :xl="16">
          <div class="hall-header">
            <h1 class="neon-title"> 🐢海龟汤 </h1>
            <a-divider class="neon-divider" />
            <p class="neon-subtitle">房间列表</p>
          </div>

          <div
            class="room-list-container"
            ref="listContainer"
          >
            <a-space
              direction="vertical"
              size="large"
              class="room-list"
              :style="{ width: '100%' }"
            >
              <a-card
                v-for="room in roomList"
                :key="room.id"
                class="room-card"
                :hoverable="true"
                @click="enterRoom(room.id!)"
              >
                <a-card-meta>
                  <template #title>
                    <span class="neon-text">{{ room.name }}</span>
                  </template>
                  <template #description>
                    <div class="room-meta">
                      <a-tag :color="getStatusColor(room.status!)" class="status-tag">
                        {{ room.status === 'waiting' ? '等待中' :
                          room.status === 'started' ? '进行中' : '已结束' }}
                      </a-tag>
                      <span class="neon-meta">
                        <user-outlined /> {{ room.currentPlayers || 0 }}人
                      </span>
                      <span class="neon-meta">
                          {{ room.name || '匿名' }}
                      </span>
                    </div>
                  </template>
                  <template #avatar>
                    <a-avatar
                      size="large"
                      class="neon-avatar"
                      :style="{ backgroundColor: '#00ff88' }"
                    >
                      {{ room.name?.charAt(0) || '?' }}
                    </a-avatar>
                  </template>
                </a-card-meta>
              </a-card>
              <div class="pagination-container">
                <a-pagination
                  v-model:current="chatRoomPage.current"
                  :total="chatRoomPage.total"
                  :page-size="chatRoomPage.pageSize"
                  show-less-items
                  show-size-changer
                  :page-size-options="['5', '10', '20', '50']"
                  @change="handlePageChange"
                  @showSizeChange="handleSizeChange"
                />
              </div>
            </a-space>
          </div>
        </a-col>
      </a-row>
    </a-layout-content>

    <a-layout-footer class="hall-footer">
      <p class="neon-footer">© 2025 海龟汤推理游戏 - 享受烧脑乐趣</p>
    </a-layout-footer>
  </a-layout>
</template>


<script setup lang="ts">
import { ref, onMounted, h } from 'vue'
import { useRouter } from 'vue-router'
import { UserOutlined, LoadingOutlined } from '@ant-design/icons-vue'
import { ChatRoomControllerService } from '../backend'
import type { ChatRoom } from '../backend'
import GlobalHeader from '../components/GlobalHeader.vue'

const router = useRouter()
const currentPage = ref(1)

const chatRoomPage = ref({
  current: 1,
  total: 1,
  pageSize: 5,
})

const roomList = ref<ChatRoom[]>([])

// 加载指示器
const indicator = h(LoadingOutlined, {
  style: {
    fontSize: '24px',
    color: '#00ff88'
  },
  spin: true,
})

// 加载房间列表
const isLoading = ref(false)
const loadRooms = async () => {
  isLoading.value = true
  try {
    const response = await ChatRoomControllerService.listChatRoomByPagesUsingPost(
      {
        current: String(chatRoomPage.value.current),
        pageSize: String(chatRoomPage.value.pageSize)
      }
    );

    roomList.value = response.data?.records || []
    chatRoomPage.value.total = Number(response.data?.total) || 1;

  } catch (error) {
    console.error('Failed to load rooms:', error)
  } finally {
    isLoading.value = false
  }
}

// 分页变化处理
const handlePageChange = (page: number) => {
  chatRoomPage.value.current = page
  loadRooms()
}

// 每页条数变化处理
const handleSizeChange = (current: number, size: number) => {
  chatRoomPage.value.pageSize = size
  chatRoomPage.value.current = 1
  loadRooms()
}

// 进入房间
const enterRoom = (roomId: string) => {
  router.push(`/room/${roomId}`)
}

// 获取状态颜色
const getStatusColor = (status: string) => {
  const statusColors: Record<string, string> = {
    'waiting': 'green',
    'started': 'volcano',
    'ended': 'gray'
  }
  return statusColors[status] || 'geekblue'
}

onMounted(() => {
  loadRooms()
})
</script>

<style scoped>

.pagination-container {
  display: flex;
  justify-content: center;
  margin-top: 24px;
}

.pagination-container :deep(.ant-pagination-item a) {
  color: #e0ffe0;
}

.pagination-container :deep(.ant-pagination-item-active) {
  border-color: #00ff88;
}

.pagination-container :deep(.ant-pagination-item-active a) {
  color: #00ff88;
}

.pagination-container :deep(.ant-pagination-options-quick-jumper input) {
  background-color: #1e1e1e;
  border-color: #333;
  color: #e0ffe0;
}

.turtle-soup-hall {
  background-color: #121212;
  min-height: 100vh;
  width: 100vw;
  color: #e0e0e0;
}

.hall-content {
  padding: 24px 0;
  background: linear-gradient(to bottom, #121212, #0a1a0a);
}

.hall-header {
  text-align: center;
  margin-bottom: 32px;
}

.neon-title {
  font-size: 2.5rem;
  color: #00ff88;
  text-shadow: 0 0 5px #00ff88, 0 0 10px #00ff88, 0 0 15px #00ff88;
  margin-bottom: 16px;
}

.neon-subtitle {
  font-size: 1.2rem;
  color: #a0ffc0;
  text-shadow: 0 0 3px #a0ffc0;
}

.neon-divider {
  border-color: #00ff88;
  opacity: 0.7;
}

.room-list-container {
  height: calc(100vh - 220px);
  overflow-y: auto;
  padding: 0 16px;
  scrollbar-width: thin;
  scrollbar-color: #00ff88 #121212;
}

.room-list-container::-webkit-scrollbar {
  width: 8px;
}

.room-list-container::-webkit-scrollbar-track {
  background: #121212;
}

.room-list-container::-webkit-scrollbar-thumb {
  background-color: #00ff88;
  border-radius: 4px;
}

.room-card {
  background-color: #1e1e1e;
  border: 1px solid #333;
  transition: all 0.3s;
  cursor: pointer;
}

.room-card:hover {
  border-color: #00ff88;
  box-shadow: 0 0 10px rgba(0, 255, 136, 0.3);
  transform: translateY(-2px);
}

.room-meta {
  display: flex;
  gap: 12px;
  align-items: center;
  flex-wrap: wrap;
}

.status-tag {
  font-weight: bold;
}

.neon-text {
  color: #e0ffe0;
  text-shadow: 0 0 2px #e0ffe0;
}

.neon-meta {
  color: #b0ffb0;
  text-shadow: 0 0 1px #b0ffb0;
}

.neon-avatar {
  box-shadow: 0 0 5px #00ff88;
}

.loading-more {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 24px 0;
  gap: 12px;
}

.neon-loading {
  color: #80ff80;
  text-shadow: 0 0 2px #80ff80;
}

.hall-footer {
  text-align: center;
  background-color: #0a0a0a;
  padding: 16px;
}

.neon-footer {
  color: #80ff80;
  text-shadow: 0 0 2px #80ff80;
  margin: 0;
}

@media (max-width: 768px) {
  .room-list-container {
    height: calc(100vh - 180px);
  }

  .neon-title {
    font-size: 2rem;
  }

  .room-meta {
    flex-direction: column;
    align-items: flex-start;
    gap: 6px;
  }
}
</style>
