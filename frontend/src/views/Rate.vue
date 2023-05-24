<template>
    <!-- 多条件搜索 -->
    <div class="search" style="padding: 10px 0;">
        <el-input :suffix-icon="Search" style="width: 200px" placeholder="姓名" v-model="name" />
        <el-input :suffix-icon="Search" style="width: 200px; margin-left: 10px;" placeholder="年龄" v-model="age" />
        <el-input :suffix-icon="Search" style="width: 200px; margin-left: 10px;" placeholder="评分" v-model="rate" />
        <el-button style="margin-left: 10px;" type="primary" @click="getPeoList">搜索</el-button>
    </div>
    <!-- 表格 -->
    <div class="table">
        <el-table :data="tableList" style="width: 100%">
            <el-table-column v-for="item in tableLabel" :key="item.prop" :label="item.label" :prop="item.prop"
                :width="item.width ? item.width : 125" />
            <el-table-column label="图片" width="120px">
                <template #default="scope">
                    <el-image v-if="scope.row.img" style="width: 70px; height: 70px"
                        :src="'http://localhost:8080/file/'+ scope.row.img" alt="" :fit="fill" preview-teleported="true"
                        :preview-src-list="['http://localhost:8080/file/'+ scope.row.img]">
                    </el-image>
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" min-width="180">
                <template v-slot="scope">
                    <el-button type="warning" round @click="ratePeo(scope.row)">评分</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
    <div style="padding: 10px 0">
        <el-pagination v-model:page-size="pageSize" v-model:current-page="pageNum" :total="total"
            :page-sizes="[2, 5, 10, 15]" layout="total, sizes, prev, pager, next, jumper"
            @size-change="handleSizeChange" @current-change="handleCurrentChange">
        </el-pagination>
    </div>
    <!-- 评分模态框 -->
    <el-dialog v-model="dialogFormVisible" title="评分" width="30%">
        <el-rate
        size="large"
        v-model="u_rate"
        :texts="['正中大菲柱', '缺陷明显', '普普通通', '小美女', '鹤立鸡群']"
        show-text
        />
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取消</el-button>
                <el-button type="primary" @click="commitRate();dialogFormVisible = false">
                    确认
                </el-button>
            </span>
        </template>
    </el-dialog>
</template>
<script setup>
    import { getCurrentInstance } from 'vue'
    import { onMounted, ref, reactive } from 'vue'
    import { Search } from '@element-plus/icons-vue'
    import { ElMessage } from 'element-plus'
    import { useStore } from 'vuex'


    const { proxy } = getCurrentInstance()
    const store = useStore()
    const tableList = ref([])
    const pageNum = ref(1)
    const pageSize = ref(5)
    const total = ref(5)
    const name = ref()
    const age = ref()
    const rate = ref() // 多条件搜索的评分
    const u_rate = ref() // 用户提交的评分
    const dialogFormVisible = ref(false)

    // 表格标签
    const tableLabel = reactive([
        {
            prop: "name",
            label: "姓名"
        },
        {
            prop: "age",
            label: "年龄"
        },
        {
            prop: "sex",
            label: "性别"
        },
        {
            prop: "birth",
            label: "出生日期",
            width: 200
        },
        {
            prop: "rate",
            label: "评分",
            width: 200
        },
    ])
    // 提交评分的记录
    const record = reactive({
        u_id: store.state.uid, // 评分的用户id
        rate: u_rate, // 评分
        p_id: null // 评分的对象id
    })
    // 打开评分模态框
    const ratePeo = (row) => {
        dialogFormVisible.value = true
        record.p_id = row.id // 更新评分对象id
    }
    // 提交评分数据到后端
    const commitRate = () => {
        let param = {
            u_id: record.u_id, 
            rate: record.rate, 
            p_id: record.p_id
        }
        let res = proxy.$api.commitRate(param)
        getPeoList()
    }
    
    const getPeoList = async () => {
        let param = {
            pageNum: pageNum.value,
            pageSize: pageSize.value,
            name: name.value,
            age: age.value,
            rate: rate.value,
        }
        let res = await proxy.$api.getPeoList(param)
        tableList.value = res.data.peoList
        total.value = res.data.total
    }
    onMounted(() => {
        getPeoList();
    })
</script>
<style>

</style>