import { createRouter, createWebHistory } from 'vue-router'
import store from '../stores'

const routes = [
  {
    path: '/',
    component: () => import('../views/Main.vue'),
    redirect: '/home',
    children: [
      {
        path: '/home',
        name: 'home',
        component: () => import('../views/home/Home.vue')
      },
      {
        path: '/userCen',
        name: 'userCen',
        component: () => import('../views/UserCen.vue')
      },
      {
        path: '/user',
        name: 'user',
        component: () => import('../views/user/User.vue')
      },
      {
        path: '/rate',
        name: 'rate',
        component: () => import('../views/Rate.vue')
      },
      {
        path: '/page2',
        name: 'page2',
        component: () => import('../views/Page2.vue')
      }
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 导航守卫
router.beforeEach((to, from, next) => {
  // 如果不存在 token
  if (!store.state.token) {
    // 如果跳转的不是登录页
    if (to.path !== '/login') {
        return next('/login')
    }
  }
  next()
})

export default router


