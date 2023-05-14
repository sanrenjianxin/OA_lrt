import { createRouter, createWebHistory } from 'vue-router'

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
        path: '/user',
        name: 'user',
        component: () => import('../views/user/User.vue')
      },
      {
        path: '/page1',
        name: 'page1',
        component: () => import('../views/Page1.vue')
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

export default router


