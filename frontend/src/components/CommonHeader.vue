<template>
    <div class="l-content">
        <!-- 图标的展示 -->
        <el-button size="small" @click="handleCollapse()">
            <el-icon :size="20">
                <Menu />
            </el-icon>
        </el-button>
        <el-breadcrumb separator="/" class="bread">
            <el-breadcrumb-item :to="{ path: '/' }"><span>首页</span></el-breadcrumb-item>
            <el-breadcrumb-item :to="current.path" v-if="current"><span>{{ current.label }}</span></el-breadcrumb-item>
        </el-breadcrumb>   
    </div>
    <div class="r-content">
        <el-dropdown>
            <span class="el-dropdown-link">
                <img class="user" src="../assets/images/avatar.png">
            </span>
            <template #dropdown>
                <el-dropdown-menu>
                    <el-dropdown-item><router-link to="/userCen" style="text-decoration: none;">个人中心</router-link></el-dropdown-item>
                    <el-dropdown-item>
                        <router-link to="/login" style="text-decoration: none;">退出</router-link>
                    </el-dropdown-item>
                </el-dropdown-menu>
            </template>
        </el-dropdown>
    </div>
</template>

<script>
    import { computed } from 'vue'
    import { useStore } from 'vuex'
    export default {
        setup() {
            let store = useStore()
            let handleCollapse = () => {
                // 调用vuex中的mutations
                store.commit("updateIsCollapse")
            }

            const current = computed(() => {
                return store.state.currentMenu
            })

            return {
                handleCollapse,
                current
            }
        }
    }
</script>

<style scoped>

.user {
    width: 40px;
    height: 40px;
    border-radius: 50%;
}
.l-content {
    display: flex;
    align-items: center;
}
.l-content > h3 {
    color: #fff;
    font-weight: normal;
}
.l-content > .el-button {
    margin-right: 20px;
}
.bread  span {
    color: #fff;
    cursor:pointer;
}
</style>