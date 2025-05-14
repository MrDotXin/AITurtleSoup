import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import { UserStore } from '@/stores/user'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/auth/LoginView.vue'),
      meta: { guestOnly: true }
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/auth/RegisterView.vue'),
      meta: { guestOnly: true }
    },
    {
      path: '/',
      redirect: '/hall',
    },
    {
      path: '/hall',
      name: 'hall',
      component: () => import('../views/HallView.vue'),
      meta:   { requiresAuth: true }
    },
    {
      path: '/room/:roomId',
      name: 'ChatRoom',
      component: () => import('../views/ChatRoom.vue'),
      meta:   { requiresAuth: true }
    },

    // 其他需要保护的路由...
  ]
})

router.beforeEach(async (to, from, next) => {
  const userStore = UserStore()

  // 获取当前用户状态
  if (!userStore.isLoggedIn) {
    await userStore.fetchCurrentUser()
  }

  // 需要登录的页面
  if (to.meta.requiresAuth && !userStore.isLoggedIn) {
    next({ name: 'login', query: { redirect: to.fullPath } })
  }
  // 仅允许未登录用户访问的页面
  else if (to.meta.guestOnly && userStore.isLoggedIn) {
    next('/')
  } else {
    next()
  }
})

export default router
