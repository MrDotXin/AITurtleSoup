<template>
  <div>
    <div class="chat-container">
      <!-- 动态背景特效 -->
      <div class="background-effects">
        <div class="grid-lines"></div>
        <div class="glowing-effects"></div>
      </div>

      <!-- 可折叠汤面展示 -->
      <details class="soup-dropdown" :open="roomStatus !== 'WAITING'">
        <summary class="dropdown-header">
          <span class="dropdown-title">📜 当前汤面</span>
          <span class="dropdown-icon">{{ roomStatus === 'WAITING' ? '（等待开始）' : '点击展开' }}</span>
        </summary>
        <div class="soup-content">
          {{ soup?.riddle || "汤面加载中..." }}
        </div>
      </details>

      <!-- 聊天室主体 -->
      <div class="chat-room">
        <div class="chat-log" ref="chatLogContainer">
          <div v-for="(message, index) in chatLogs" :key="index"
            :class="['message-container', message.userId === user.loginUser?.id ? 'user' : 'ai']">
            <div class="avatar">
              <img src="@/assets/avatar_default.png" />
            </div>
            <div class="message-bubble">
              {{ message.content }}
            </div>
          </div>

          <!-- AI加载状态 -->
          <div v-if="isLoadingAIResponse" class="message-container ai">
            <div class="avatar">
              <img src="@/assets/avatar_default.png" />
            </div>
            <div class="loading-bubble">
              <div class="loading-dots">
                <div class="dot"></div>
                <div class="dot"></div>
                <div class="dot"></div>
              </div>
            </div>
          </div>
        </div>

        <!-- 输入区域 -->
        <div class="chat-input" :class="{ disabled: isLoadingAIResponse }">
          <a-input v-model:value="userMessage" placeholder="输入消息..." :disabled="isLoadingAIResponse"
            @pressEnter="sendMessage" />
          <a-button type="primary" :loading="isLoadingAIResponse" @click="sendMessage">
            {{ isLoadingAIResponse ? '思考中' : '发送' }}
          </a-button>
        </div>

        <transition name="v">
          <div
            v-if="roomStatus !== 'started'"
            class="start-game-button"

          >
            <div class="pulse-effect"></div>
              <a-space direction="vertical">
                <a-button type="primary" size="large" @click="modalVisible=true">
                    切换海龟汤
                </a-button>
                <a-button type="primary" size="large" @click="startGame">
                  🚀 开始游戏
                </a-button>
            </a-space>
          </div>
          <div
            v-else class="start-game-button"
            @click="endGame"
          >
            <div class="pulse-effect"></div>
            <a-button type="primary" size="large">
              结束游戏
            </a-button>
          </div>
        </transition>
      </div>
    </div>
  </div>

  <TurtleSoupSelectModal :visible="modalVisible" @onSelectSoup="onSelectSoup" @onCancel="modalVisible=false" />
</template>


<script setup lang="ts">
import { ref, onMounted, nextTick, watchEffect } from 'vue'
import { useRoute } from 'vue-router'
import {
  ChatControllerService,
  TurtleSoupControllerService,
  ChatRoomControllerService,
  ChatLogControllerService
} from '../backend'

import type { TurtleSoup, ChatLog } from '../backend'
import { UserStore } from '../stores/user'

import TurtleSoupSelectModal from '@/components/TurtleSoupSelectModal.vue'

const route = useRoute()
const user = UserStore();
// 房间信息
const roomId = ref<string>(route.params.roomId as string)
const roomStatus = ref<string>('waiting') // 房间状态：waiting | started | ended

// 汤面和汤底
const soup = ref<TurtleSoup | null>(null)

// 聊天记录
const chatLogs = ref<ChatLog[]>([])
const userMessage = ref<string>('')
const isLoadingAIResponse = ref<boolean>(false)

// 海龟汤切换
const modalVisible = ref(false);

const onSelectSoup = async (selectSoup : TurtleSoup) => {
    const response = await ChatRoomControllerService.updateTurtleSoupUsingPost(roomId.value, selectSoup.id || '')
    if (response.data) {
      soup.value = selectSoup;

      roomStatus.value = 'waiting';
      modalVisible.value = false;
    }
}

// 获取房间状态和汤面
onMounted(async () => {
  try {
    // 假设后端提供获取房间状态的接口
    const roomResponse = await ChatRoomControllerService.getChatRoomByIdUsingGet(roomId.value)
    roomStatus.value = roomResponse.data?.status || 'waiting'

    // 获取汤面
    const soupResponse = await TurtleSoupControllerService.getTurtleSoupByIdUsingGet(roomResponse.data?.soupId || '')
    console.log(soupResponse);
    soup.value = soupResponse.data || null;


    // 加载聊天记录
    const chatLogResponse = await ChatLogControllerService.getAllGameChatLogsUsingGet(roomId.value)

    if (chatLogResponse.data) {
      chatLogs.value = chatLogResponse.data;
    }

  } catch (error) {
    console.error('Failed to load room or soup:', error)
  }
})

// 开始游戏方法
const startGame = async () => {
  try {
    await ChatControllerService.startGameUsingPost(roomId.value)
    roomStatus.value = 'started'
    chatLogs.value = [];
    pushMessage('游戏开始', true);


  } catch (error) {
    console.error('开始游戏失败:', error)
    // 可以添加失败提示
  }
}
// 结束游戏
const endGame = async () => {
  try {
    await ChatControllerService.endGameUsingPost(roomId.value)
    roomStatus.value = 'ended'
    // 可以添加结束游戏成功的提示或音效

    if (soup?.value?.solution) {
      pushMessage("游戏结束!", true)
      pushMessage(soup.value.solution, true)
    }
  } catch (error) {
    console.error('结束游戏失败:', error)
    // 可以添加失败提示
  }
}


// 发送消息
const sendMessage = async () => {
  if (!userMessage.value.trim()) return

  // 添加用户消息到聊天记录
  pushMessage(userMessage.value, false)

  // // 滚动到底部
  // scrollToBottom()

  let msg = userMessage.value;
  // 清空输入框
  userMessage.value = ''

  // 如果房间状态为 WAITING，直接返回
  if (roomStatus.value !== 'started') return

  // 显示加载动画
  isLoadingAIResponse.value = true

  try {
    // 请求 AI 回复
    const response = await ChatControllerService.postQueryByIdUsingPost(roomId.value, msg)
    const aiMessage : string = response.data?.content || ''
    if (aiMessage.match(/^.*\[GG\].*$/)) {
      endGame();
    } else {
      msg = soup?.value?.solution || ''
      // 添加 AI 消息到聊天记录
      pushMessage(aiMessage, true)
    }
  } catch (error) {
    console.error('Failed to send message:', error)
    chatLogs.value.push({
      content: 'AI回复失败,请稍后再试。',
      isQuestion: 0,
      messageType: 'AI',
      sentTime: new Date().toISOString(),
    })
  } finally {
    // 隐藏加载动画
    isLoadingAIResponse.value = false
  }
}


const pushMessage = (message: string, isAI : boolean) => {
  chatLogs.value.push({
    content: message,
    isQuestion: isAI ? 0 : 1,
    messageType: isAI ? 'AI' : 'USER',
    userId: isAI ? '0' : user.loginUser?.id,
    sentTime: new Date().toISOString(),
  })
}

// 滚动到底部
const scrollToBottom = () => {
  nextTick(() => {
    const chatLogContainer = document.querySelector('.chat-log')
    if (chatLogContainer) {
      chatLogContainer.scrollTop = chatLogContainer.scrollHeight
    }
  })
}

watchEffect(() => {
  if (chatLogs.value.length) {
    scrollToBottom();
  }
});

</script>

<style scoped lang="scss">
.chat-container {
  position: relative;
  min-height: 100vh;
  width: 100vw;
  margin: 0px;
  padding: 0px;
  background: #1e1d1d;
  color: rgb(11, 247, 11);
  overflow: hidden;
  font-family: 'Courier New', monospace;

  .background-effects {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    pointer-events: none;

    .grid-lines {
      position: absolute;
      width: 100%;
      height: 100%;
      background-image:
        linear-gradient(rgba(0, 255, 102, 0.104) 1px, transparent 1px),
        linear-gradient(90deg, rgba(0, 255, 100, 0.05) 1px, transparent 1px);
      background-size: 20px 20px;
    }

    .glowing-effects {
      position: absolute;
      width: 100%;
      height: 100%;
      background: radial-gradient(circle at 50% 50%, rgba(0, 255, 100, 0.1), transparent 60%);
      animation: pulse 3s infinite;
    }
  }

  .soup-dropdown {
    margin-bottom: 20px;
    border: 1px solid rgba(0, 255, 102, 0.699);
    border-radius: 4px;

    .dropdown-header {
      padding: 12px;
      background: rgba(0, 20, 15, 0.8);
      cursor: pointer;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .soup-content {
      padding: 16px;
      background: rgba(0, 30, 20, 0.9);
      border-top: 1px solid rgba(6, 248, 103, 0.2);
    }
  }

  .chat-room {
    max-width: 70%;
    margin: 0 auto;
    padding: 20px; // 内部留白
    width: 100%;
    position: relative;
    z-index: 1;

    .chat-log {
      height: calc(90vh - 150px); // 动态高度
      overflow-y: auto;
      padding: 20px;
      background: rgba(0, 20, 15, 0.8);
      border: 1px solid rgba(0, 255, 100, 0.2);
      border-radius: 8px;
      margin-bottom: 20px;

      .message-container {
        display: flex;
        gap: 12px;
        margin: 16px 0;

        &.user {
          flex-direction: row-reverse;

          .message-bubble {
            background: rgba(0, 255, 132, 0.303);
            border-radius: 12px 12px 0 12px;
          }
        }

        &.peer {
          flex-direction: row-reverse;

          .message-bubble {
            background: rgba(0, 255, 195, 0.303);
            border-radius: 12px 12px 0 12px;
          }
        }

        &.ai {
          .message-bubble {
            background: rgba(255, 255, 255, 0.05);
            border-radius: 12px 12px 12px 0;
          }
        }

        .avatar {
          width: 40px;
          height: 40px;
          flex-shrink: 0;

          img {
            width: 100%;
            height: 100%;
            border-radius: 50%;
            object-fit: cover;
            border: 2px solid rgba(0, 255, 100, 0.5);
          }
        }

        .message-bubble {
          max-width: 70%;
          padding: 12px 16px;
          position: relative;
          word-break: break-word;
          box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
        }

        .loading-bubble {
          padding: 12px 16px;

          .loading-dots {
            display: flex;
            gap: 8px;

            .dot {
              width: 8px;
              height: 8px;
              background: rgba(0, 255, 100, 0.8);
              border-radius: 50%;
              animation: bounce 1.4s infinite;

              &:nth-child(2) {
                animation-delay: 0.2s;
              }

              &:nth-child(3) {
                animation-delay: 0.4s;
              }
            }
          }
        }
      }
    }

    .chat-input {
      display: flex;
      gap: 12px;

      &.disabled {
        opacity: 0.6;
        pointer-events: none;
      }

      :deep(.ant-input) {
        background: rgba(0, 30, 20, 0.9);
        border: 1px solid rgba(0, 255, 100, 0.3);
        color: #0f0;

        &:focus {
          box-shadow: 0 0 8px rgba(0, 255, 100, 0.3);
        }
      }

      :deep(.ant-btn-primary) {
        background: rgba(0, 255, 100, 0.8);
        border-color: transparent;
        font-weight: bold;
        color: #000;

        &:hover {
          background: rgba(0, 255, 100, 1);
        }
      }
    }
  }
}

// 添加消息对齐修正
.message-container {
  &.ai {
    margin-left: -40px;
    padding-left: 40px;
  }

  &.user {
    margin-right: -40px;
    padding-right: 40px;
  }
}

@keyframes pulse {
  0% {
    opacity: 0.3;
  }

  50% {
    opacity: 0.6;
  }

  100% {
    opacity: 0.3;
  }
}

@keyframes bounce {

  0%,
  80%,
  100% {
    transform: translateY(0);
  }

  40% {
    transform: translateY(-10px);
  }
}


// 修正后的悬浮按钮样式
.start-game-button {
  position: fixed;
  bottom: 100px;
  right: 40px;
  z-index: 100;
  display: flex;
  justify-content: center;
  align-items: center;

  // 按钮容器添加初始状态
  opacity: 1;
  transition: opacity 0.5s;

  // 修正后的按钮样式
  .ant-btn {
    position: relative; // 确保按钮在脉冲效果上方
    font-size: 18px;
    height: 50px;
    padding: 0 30px;
    background: linear-gradient(135deg, #00ff88, #00b3ff);
    border: none;
    box-shadow: 0 4px 15px rgba(0, 255, 136, 0.3);
    transition: all 0.3s;
    z-index: 2; // 确保按钮在脉冲效果上方

    &:hover {
      transform: translateY(-3px);
      box-shadow: 0 6px 20px rgba(0, 255, 136, 0.4);
    }
  }

  // 修正后的脉冲效果
  .pulse-effect {
    position: absolute;
    width: calc(100% + 20px);
    height: calc(100% + 20px);
    border-radius: 50%;
    background: rgba(0, 255, 136, 0.2);
    animation: pulse 2s infinite;
    z-index: 1;
    pointer-events: none; // 防止脉冲层拦截点击事件
  }
}

// 简化过渡动画（移除单独的fade类）
.v-enter-active,
.v-leave-active {
  transition: opacity 0.5s;
}
.v-enter-from,
.v-leave-to {
  opacity: 0;
}

// 修正脉冲动画
@keyframes pulse {
  0% {
    transform: scale(0.8);
    opacity: 0.5;
  }
  70% {
    transform: scale(1.3);
    opacity: 0.1;
  }
  100% {
    transform: scale(1.3);
    opacity: 0;
  }
}

</style>
