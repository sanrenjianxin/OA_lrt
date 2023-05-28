import { createRouter, createWebHistory } from 'vue-router'
import store from '../stores'
import request from "../api/request";


const routes = [
  {
    path: '/',
    component: () => import('../views/Main.vue'),
    redirect: '/home',
    children: [
      {
        path: '/home',
        name: 'home',
        component: () => import('../views/home/Home.vue'),
        meta: {requireAuth: true} 
        
      },
      {
        path: '/userCen',
        name: 'userCen',
        component: () => import('../views/UserCen.vue'),
        meta: {requireAuth: true} 
      },
      {
        path: '/peo',
        name: 'peo',
        component: () => import('../views/peo/Peo.vue'),
        meta: {requireAdminAuth: true} 
      },
      {
        path: '/rate',
        name: 'rate',
        component: () => import('../views/Rate.vue'),
        meta: {requireAuth: true} 

      },
      {
        path: '/page2',
        name: 'page2',
        component: () => import('../views/Page2.vue'),
        meta: {requireAuth: true} 
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
router.beforeEach( async (to) => {  
    if (to.name !== "login") {
      if (to.meta.requireAuth) {
        await request({
          url: '/user/loginConfirm',
          method: 'get'
        })
      }
      if(to.meta.requireAdminAuth) {
        await request({
          url: '/user/AdminLoginConfirm',
          method: 'get'
        })
      }
    }
})

export default router


