<template>
    <div class="search" style="padding: 10px 0;">
        <el-input :suffix-icon="Search" style="width: 200px" placeholder="姓名" v-model="name" />
        <el-input :suffix-icon="Search" style="width: 200px; margin-left: 10px;" placeholder="年龄" v-model="age" />
        <el-input :suffix-icon="Search" style="width: 200px; margin-left: 10px;" placeholder="评分" v-model="rate" />
        <el-button style="margin-left: 10px;" type="primary" @click="getPeoList">搜索</el-button>
    </div>
    <div class="user-header" style="margin: 10px 0;">
        <el-button type="primary" @click="dialogFormVisible = true">+新增</el-button>
        <el-button type="danger" @click="batchDelete">-批量删除</el-button>
        <el-upload class="upload-demo" :action="uri + 'people/import'" :show-file-list="false"
            style="display: inline-block;" :on-success="handleImpsuccess">
            <el-button type="primary" style="margin:0 10px;">导入<el-icon>
                    <Upload />
                </el-icon></el-button>
        </el-upload>

        <el-button type="primary" @click="exp">导出<el-icon>
                <Download />
            </el-icon></el-button>
    </div>
    <div class="table">
        <el-table :data="list" style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" />
            <el-table-column v-for="item in tableLabel" :key="item.prop" :label="item.label" :prop="item.prop"
                :width="item.width ? item.width : 125" />
            <el-table-column label="图片" width="120px">
                <template #default="scope">
                    <el-image 
                    v-if="scope.row.img" 
                    style="width: 70px; height: 70px" 
                    :src="uri+'file/'+ scope.row.img" 
                    alt="" 
                    :fit="fill"
                    preview-teleported="true"
                    :preview-src-list="[uri+ 'file/'+ scope.row.img]" >
                    </el-image>
                </template>
            </el-table-column>
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
            <el-form-item label="评分">
                <el-input v-model="pform.rate" autocomplete="off" />
            </el-form-item>
            <el-form-item label="图片">
                <el-upload class="upload-demo" :action= "uri + 'file/upload'" :show-file-list="picture"
                    style="display: inline-block;" :on-success="handleIMGsuccess">
                    <el-button type="primary" style="margin:0 10px;">上传图片</el-button>
                    <template #tip>
                        <div class="el-upload__tip">
                            jpg/png files with a size less than 500kb
                        </div>
                    </template>
                </el-upload>
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
    import config from '../../config'

    export default {
        setup() {
            // 切换uri
            const uri = config.DEV;
            const { proxy } = getCurrentInstance()
            const isEdit = ref(false) // 判断是编辑还是新增
            const list = ref([])
            const pageNum = ref(1)
            const pageSize = ref(5)
            const total = ref(5)
            const name = ref()
            const age = ref()
            const rate = ref()
            let dialogFormVisible = ref(false)
            const mutiSelection = ref() // 批量删除的数据
            let pform = ref({
                id: null,
                name: '',
                age: '',
                sex: '',
                birth: '',
                rate: '',
                img: ''
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
                    prop: "rate",
                    label: "评分",
                    width: 200
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
                    rate: rate.value,
                }
                let res = await proxy.$api.getPeoList(param)
                list.value = res.data.peoList
                total.value = res.data.total
            }
            const addPeo = async () => {
                let param = {
                    name: pform.value.name,
                    age: pform.value.age,
                    sex: pform.value.sex,
                    birth: pform.value.birth,
                    rate: pform.value.rate,
                    img: pform.value.img
                }
                // 如果是编辑的话，就在请求参数中加上id
                if (isEdit.value == true) {
                    param.id = pform.value.id
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
            const editPeo = (row) => {
                pform.value = JSON.parse(JSON.stringify(row)) // 将行中的数据赋给pform JSON.parse(JSON.stringify(row))使双向绑定暂时失效
                console.log(pform);
                isEdit.value = true
                dialogFormVisible.value = true
            }
            const deletePeo = async (d_id) => {
                let param = {
                    id: d_id
                }
                let res = await proxy.$api.deletePeo(param)
                if (res.data.code === "200") {
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
                mutiSelection.value = val
            }
            // 点击批量删除按钮
            const batchDelete = async () => {
                // 转换为id数组
                const ids = mutiSelection.value.map(v => v.id)
                if (ids.length > 0) {
                    let res = await proxy.$api.batchDel(ids);
                    if (res.data.code === "200") {
                        ElMessage({
                            message: '批量删除成功',
                            type: 'success',
                        })
                        getPeoList()
                    }
                }
            }
            // 导出按钮
            const exp = () => {
                window.open(uri + "people/export")
            }
            // 导入成功后执行
            const handleImpsuccess = () => {
                ElMessage({
                    message: '导入成功',
                    type: 'success',
                })
                getPeoList()
            }
            // 导入文件成功后获取文件存储名
            const handleIMGsuccess = (res) => {
                pform.value.img = res.data
                ElMessage({
                    message: '上传成功',
                    type: 'success',
                })
            }
            return {
                uri,
                list,
                tableLabel,
                Search,
                pageNum,
                pageSize,
                total,
                name,
                age,
                rate,
                dialogFormVisible,
                pform,
                handleSizeChange,
                handleCurrentChange,
                getPeoList,
                addPeo,
                editPeo,
                deletePeo,
                handleSelectionChange,
                batchDelete,
                exp,
                handleImpsuccess,
                handleIMGsuccess
            }
        }
    }
</script>

<style>
</style>