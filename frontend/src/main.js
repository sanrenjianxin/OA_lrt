import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import store from './stores/index.js'
import api from './api/api.js'

const app = createApp(App)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }

app.config.globalProperties.$api = api
app.use(ElementPlus)
app.use(createPinia())
app.use(router)
app.use(store)


app.mount('#app')
