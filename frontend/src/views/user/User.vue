<template>
    <div class="search" style="padding: 10px 0;">
        <el-input :suffix-icon="Search" style="width: 200px" placeholder="姓名" v-model="name" />
        <el-input :suffix-icon="Search" style="width: 200px; margin-left: 10px;" placeholder="年龄" v-model="age" />
        <el-input :suffix-icon="Search" style="width: 200px; margin-left: 10px;" placeholder="住址" v-model="addr" />
        <el-button style="margin-left: 10px;" type="primary" @click="getPeoList">搜索</el-button>
    </div>
    <div class="user-header" style="margin: 10px 0;">
        <el-button type="primary" @click="dialogFormVisible = true">+新增</el-button>
        <el-button type="danger">-批量删除</el-button>
        <el-button type="primary">导入<el-icon>
                <Upload />
            </el-icon></el-button>
        <el-button type="primary">导出<el-icon>
                <Download />
            </el-icon></el-button>
    </div>
    <div class="table">
        <el-table :data="list" style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" />
            <el-table-column v-for="item in tableLabel" :key="item.prop" :label="item.label" :prop="item.prop"
                :width="item.width ? item.width : 125" />
            <el-table-column fixed="right" label="操作" min-width="180">
                <template v-slot="scope">
                    <el-button size="small" @click="editPeo(scope.row)">编辑</el-button>
                    <el-button type="danger" size="small" @click="deletePeo(scope.row.id)">删除</el-button>
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
    <el-dialog v-model="dialogFormVisible" title="人员信息" width="30%">
        <el-form label-width="100px">
            <el-form-item label="姓名">
                <el-input v-model="pform.name" autocomplete="off" />
            </el-form-item>
            <el-form-item label="年龄">
                <el-input v-model="pform.age" autocomplete="off" />
            </el-form-item>
            <el-form-item label="性别">
                <el-radio-group v-model="pform.sex">
                    <el-radio label="男" />
                    <el-radio label="女" />
                </el-radio-group>
            </el-form-item>
            <el-form-item label="出生日期">
                <el-date-picker v-model="pform.birth" type="date" placeholder="请选择你的出生日期" style="width: 100%" />
            </el-form-item>
            <el-form-item label="住址">
                <el-input v-model="pform.addr" autocomplete="off" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取消</el-button>
                <el-button type="primary" @click="addPeo();dialogFormVisible = false">
                    确认
                </el-button>
            </span>
        </template>
    </el-dialog>

</template>

<script>
    import { getCurrentInstance } from 'vue'
    import { onMounted, ref, reactive } from 'vue'
    import { Search } from '@element-plus/icons-vue'
    import { ElMessage } from 'element-plus'

    export default {
        setup() {
            const { proxy } = getCurrentInstance()
            const isEdit = ref(false) // 判断是编辑还是新增
            const list = ref([])
            const pageNum = ref(1)
            const pageSize = ref(10)
            const total = ref(10)
            const name = ref()
            const age = ref()
            const addr = ref()
            const dialogFormVisible = ref(false)
            const pform = reactive({
                id: null,
                name: '',
                age: '',
                sex: '',
                birth: '',
                addr: ''
            })
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
                    prop: "addr",
                    label: "住址",
                    width: 320
                },
            ])
            onMounted(() => {
                getPeoList();
            })
            const getPeoList = async () => {
                let param = {
                    pageNum: pageNum.value,
                    pageSize: pageSize.value,
                    name: name.value,
                    age: age.value,
                    addr: addr.value
                }
                let res = await proxy.$api.getPeoList(param)
                list.value = res.data.peoList
                total.value = res.data.total
            }
            const addPeo = async () => {
                let param = {
                    name: pform.name,
                    age: pform.age,
                    sex: pform.sex,
                    birth: pform.birth,
                    addr: pform.addr
                }
                // 如果是编辑的话，就在请求参数中加上id
                if(isEdit.value == true){
                    param.id = pform.id
                }
                let res = await proxy.$api.savePeo(param)
                if (res.data == 1) {
                    ElMessage({
                        message: '保存成功',
                        type: 'success',
                    })
                    getPeoList()
                }
                else {
                    ElMessage.error('保存失败')
                }
                isEdit.value = false // 将isEdit重置为false,避免下次新增数据提交了id
            }
            const editPeo = async (row) => {
                pform.value = JSON.parse(JSON.stringify(row)) // 将行中的数据赋给pform JSON.parse(JSON.stringify(row))使双向绑定暂时失效
                isEdit.value = true
                dialogFormVisible.value = true
            }
            const deletePeo = async (d_id) => {
                let param = {
                    id: d_id
                }
                let res = await proxy.$api.deletePeo(param)
                if (res.data == 1) {
                    ElMessage({
                        message: '删除成功',
                        type: 'success',
                    })
                    getPeoList()
                }
                else {
                    ElMessage.error('删除失败')
                }
            }
            const handleSizeChange = (pagesize) => {
                pageSize.value = pagesize
                getPeoList()
            }
            const handleCurrentChange = (currentpage) => {
                pageNum.value = currentpage
                getPeoList()
            }
            const handleSelectionChange = (val) => {
                // TODO 发送id的数组
            }
            return {
                list,
                tableLabel,
                Search,
                pageNum,
                pageSize,
                total,
                name,
                age,
                addr,
                dialogFormVisible,
                pform,
                handleSizeChange,
                handleCurrentChange,
                getPeoList,
                addPeo,
                editPeo,
                deletePeo,
                handleSelectionChange
            }
        }
    }
</script>

<style>
</style>