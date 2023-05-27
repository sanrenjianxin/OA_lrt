<template>
    <el-card style="width: 500px; padding: 20px;">
        <el-form label-width="100px">
            <el-form-item label="用户名">
                <el-input v-model="pform.username" autocomplete="off" disabled />
            </el-form-item>
            <el-form-item label="昵称">
                <el-input v-model="pform.nickname" autocomplete="off" />
            </el-form-item>
            <el-form-item label="联系方式">
                <el-input v-model="pform.phone" autocomplete="off" />
            </el-form-item>
            <el-form-item label="邮箱">
                <el-input v-model="pform.email" autocomplete="off" />
            </el-form-item>
        </el-form>
    </el-card>
</template>
<script>
    import { onMounted, ref, reactive } from 'vue'
    import { getCurrentInstance } from 'vue'
    import { ElMessage } from 'element-plus'
    import {useStore} from 'vuex'
    export default {
        setup() {
            const store = useStore()
            const {proxy} = getCurrentInstance()
            const pform = ref({
                username: null,
                nickname: null,
                phone: null,
                email: null,
            })
            const getUserInfo = async () => {
                let param = {
                    id: store.state.uid
                }
                let res = await proxy.$api.getIUserInfo(param)
                if(res.data.code === "200") {
                    pform.value = res.data.data
                }
                else {
                    ElMessage({
                        message: res.data.msg,
                        type: 'error'
                    })
                }
            }
            onMounted(() => {
                getUserInfo()
            })
            return {
                pform
            }
        }
    }
</script>
<style>

</style>