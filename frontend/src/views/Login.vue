<template>
    <div class="wrapper">
        <div
            style="margin: 200px auto; background-color: #fff; width: 350px; height: 250px; padding: 20px; border-radius: 10px;">
            <div style="margin: 20px 0; text-align: center; font-size: 24px;"><b>登 录</b></div>
            <el-input size="medium" style="margin: 10px 0;height: 40px;" prefix-icon="User"
                v-model="user.username"></el-input>
            <el-input size="medium" style="margin: 10px 0;height: 40px;" prefix-icon="Lock" show-password
                v-model="user.password"></el-input>
            <div style="margin: 10px 0; text-align: right;">
                <el-button type="primary" size="medium" autocomplete="off" @click="login">登录</el-button>
                <el-button type="warning" size="medium" autocomplete="off">注册</el-button>
            </div>
        </div>
    </div>
</template>
<script setup>
    import { User, Lock } from "@element-plus/icons-vue"
    import { ref, reactive, getCurrentInstance } from 'vue'
    import { ElMessage } from 'element-plus'
    import { useStore } from 'vuex'
    import request from '../api/request'

    const { proxy } = getCurrentInstance()
    const store = useStore()
    const user = reactive({
        username: null,
        password: null
    })
    const login = async () => {
        if (user.username == null || user.password == null) {
            ElMessage({
                message: '用户名或密码不能d为空',
                type: 'error'
            })
            return;
        }
        let param = {
            username: user.username,
            password: user.password
        }
        let res = await proxy.$api.userLogin(param)
        if (res.data.code === '200') {
            proxy.$router.push("/")
            store.commit('updateToken', res.data.data.token)
            store.commit('updateUsername', res.data.data.username)
            store.commit('updateUid', res.data.data.id)
            store.commit('updateRole', res.data.data.role)
        }
        else {
            ElMessage({
                message: res.data.msg,
                type: 'error'
            })
        }
    }


</script>
<style>
    .wrapper {
        height: 100vh;
        background-image: linear-gradient(to bottom right, #FC466B, #3F5EFB);
        overflow: hidden;
    }
</style>