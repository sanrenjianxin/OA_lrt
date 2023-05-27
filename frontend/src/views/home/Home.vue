<template>
    <el-row :gutter="20">
        <el-col :span="8" style="margin-top: 20px;">
            <el-card shadow="hover">
                <div class="user-">
                    <img src="../../assets/images/avatar.png" />
                    <div class="user-info">
                        <p class="name">{{ store.state.username }}</p>
                        <p class="role">普通用户</p>
                    </div>
                </div>
                <div class="login-info">
                    <p>上次登录时间:<span>2023.3.25</span></p>
                    <p>上次登录地点:<span>杭州</span></p>
                </div>
            </el-card>
            <el-card shadow="hover" style="margin-top: 20px;" height="450px">

            </el-card>
        </el-col>
        <el-col :span="8" style="margin-top: 20px;">
            <el-card shadow="hover" height="450px">
                <div id="pie" :style="{ width: '450px', height: '450px' }"></div>
            </el-card>
        </el-col>
    </el-row>
</template>

<script setup>
    import * as echarts from "echarts";
    import { defineComponent, getCurrentInstance, getCurrentScope, onMounted, ref, reactive } from 'vue'
    import { useStore } from 'vuex'
    import { ElMessage } from 'element-plus'

    const store = useStore()
    const { proxy } = getCurrentInstance()

    let pieOption = reactive({
        title: {
            text: '各分段人数',
            left: 'center'
        },
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left'
        },
        series: [
            {
                name: 'Access From',
                type: 'pie',
                radius: '50%',
                data: [
                    { value: 1048, name: '1分' },
                    { value: 735, name: '2分' },
                    { value: 580, name: '3分' },
                    { value: 484, name: '4分' },
                    { value: 300, name: '5分' }
                ],
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    })

    const initPie = async () => {
        var chartDom = document.getElementById('pie');
        var myChart = echarts.init(chartDom);
        let res = await proxy.$api.getPieData()
        for (var i = 0; i <= 4; i++){
            pieOption.series[0].data[i].value = res.data.data[i]
        }
        myChart.setOption(pieOption);
    }
    onMounted(() => {
        initPie()
    })




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