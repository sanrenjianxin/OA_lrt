import { createStore } from 'vuex'

export default createStore({
    state: {
        isCollapse: false, // 控制侧边栏是否折叠
        currentMenu: null
    },
    mutations: {
        updateIsCollapse(state, payload) {
            state.isCollapse = !state.isCollapse
        },
        selectMenu(state, val) {
            val.name == 'home' ? (state.currentMenu = null) : (state.currentMenu = val)
        }
    }
})