import { createStore } from 'vuex'
import creteState from 'vuex-persistedstate'

export default createStore({
    // 配置哪些状态需要持久化
    plugins: [creteState({
        storage: window.localStorage,
        reducer (state) {
          return {
            token: state.token,
            username: state.username,
            uid: state.uid,
            role: state.role
          }
        }
      }
    )],
    state: {
        isCollapse: false, // 控制侧边栏是否折叠
        currentMenu: null,
        token: null,
        username: null,
        uid: null,
        role: null
    },
    mutations: {
        updateIsCollapse(state, payload) {
            state.isCollapse = !state.isCollapse
        },
        selectMenu(state, val) {
            val.name == 'home' ? (state.currentMenu = null) : (state.currentMenu = val)
        },
        updateToken(state, token) {
            state.token = token
        },
        updateUsername(state, username) {
            state.username = username
        },
        updateUid(state, uid) {
            state.uid = uid
        },
        updateRole(state, role) {
            state.role = role
        }
    }
})