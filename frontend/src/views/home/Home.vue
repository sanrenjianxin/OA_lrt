<template>
    <el-row :gutter="20">
        <el-col :span="8" style="margin-top: 20px;">
            <el-card shadow="hover">
                <div class="user-">
                    <img src="../../assets/images/avatar.png" />
                    <div class="user-info">
                        <p class="name">Admin</p>
                        <p class="role">超级管理员</p>
                    </div>
                </div>
                <div class="login-info">
                    <p>上次登录时间:<span>2023.3.25</span></p>
                    <p>上次登录地点:<span>杭州</span></p>
                </div>
            </el-card>
            <el-card shadow="hover" style="margin-top: 20px;" height="450px">
                <el-table :data="tableData">
                    <el-table-column v-for="(value, key) in tableLabel" :key="key" :prop="key"
                        :label="value"></el-table-column>
                </el-table>
            </el-card>
        </el-col>
        <el-col :span="16" style="margin-top: 20px;">
            <div class="num">
                <el-card class="group" :body-style="{display: 'flex', padding: 0}" v-for="item in countData"
                    :key="item.name">
                    <component class="icons" :is="item.icon" :style="{background: item.color}"></component>
                    <div class="details">
                        <p class="num">￥{{ item.value }}</p>
                        <p class="txt">{{ item.name }}</p>
                    </div>
                </el-card>
            </div>
            <el-card style="height: 280px">
                <div ref="echarts" style="height: 280px"></div>
            </el-card>
        </el-col>
    </el-row>
</template>

<script>

    import axios from 'axios' // 引入axios
    import { defineComponent, getCurrentInstance, getCurrentScope, onMounted, ref } from 'vue'
    export default {
        setup() {
            const { proxy } = getCurrentInstance() // proxy 类似vue2 中this
            let tableData = ref([])
            let countData = ref([])
            const tableLabel = {
                name: "手机",
                todayBuy: "今日购买",
                monthBuy: "本月购买",
                totalBuy: "总购买"
            }

            // 发送ajax请求 同时注意连接后端后tableData要用ref改为响应式
            const getTableList = async () => {
                // await axios.get("https://www.fastmock.site/mock/2813bf740c0964d8e2d621f759a85982/api/home/getTableData").then((res)=>{
                //     if(res.data.code == 200){
                //         tableData.value = res.data.data
                //     }
                // })
                let res = await proxy.$api.getTableData() // 使用自己二次封装的axios
                tableData.value = res
            }
            const getCountData = async () => {
                let res = await proxy.$api.getCountData()
                countData.value = res
            }
            // 组件挂载时调用发送请求函数
            // onMounted(() => {
            //     getTableList()
            //     getCountData()
            // })
            // 关于echarts表格的渲染部分
            
            return {
                tableData,
                tableLabel,
                countData
            }
        }
    }
</script>

<style scoped>
    
    .user- {
        display: flex;
        align-items: center;
        padding-bottom: 20px;
        border-bottom: 1px solid #ccc;
        margin-bottom: 20px;
    }

    .user->img {
        width: 150px;
        height: 150px;
        border-radius: 50%;
        margin-right: 40px;
    }

    .login-info>p {
        line-height: 30px;
        font-size: 14px;
        color: #999;
    }

    .login-info>p>span {
        color: #666;
        margin-left: 70px;
    }

    .group {
        height: 80px;
    }

    .num {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
    }

    .num>.el-card {
        width: 32%;
        margin-bottom: 20px;
    }

    .icons {
        width: 80px;
        height: 80px;
        font-size: 30px;
        text-align: center;
        line-height: 80px;
        color: #fff;
    }

    .details {
        height: 80px;
        margin-left: 15px;
        display: flex;
        flex-direction: column;
        justify-content: center;
    }

    .details>.num {
        margin-top: 10px;
        font-size: 30px;
        margin-bottom: 10px;
    }

    .details>.txt {
        font-size: 14px;
        text-align: center;
        color: #999;
    }
</style>