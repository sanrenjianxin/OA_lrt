<template>
    <div class="wrapper">
        <div style="margin: 200px auto; background-color: #fff; width: 350px; height: 250px; padding: 20px; border-radius: 10px;">
            <div style="margin: 20px 0; text-align: center; font-size: 24px;"><b>登 录</b></div>
            <el-input size="medium" style="margin: 10px 0;height: 40px;" prefix-icon="User" v-model="user.username"></el-input>
            <el-input size="medium" style="margin: 10px 0;height: 40px;" prefix-icon="Lock" show-password v-model="user.password"></el-input>
            <div style="margin: 10px 0; text-align: right;">
                <el-button type="primary" size="medium" autocomplete="off">登录</el-button>
                <el-button type="warning" size="medium" autocomplete="off">注册</el-button>
            </div>
        </div>
    </div>
</template>
<script>
    import {User, Lock} from "@element-plus/icons-vue"
    import { onMounted, ref, reactive } from 'vue'
    
    export default {
        setup() {
            const user = reactive({
                username: null,
                password: null
            })
            return {
                user
            }
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