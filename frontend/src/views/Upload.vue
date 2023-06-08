<template lang="">
    <el-row>
        <el-col :span="12">
            <el-card style="width: 500px; padding: 20px;">
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
                        <el-date-picker v-model="pform.birth" type="date" placeholder="请选择出生日期" style="width: 100%" />
                    </el-form-item>
                    <el-form-item label="评分">
                        <el-input v-model="pform.rate" autocomplete="off" />
                    </el-form-item>
                    <el-form-item label="图片">
                        <el-upload class="upload-demo" :action="uri + 'file/upload'" :show-file-list="picture"
                            style="display: inline-block;" :on-success="handleIMGsuccess">
                            <el-button type="primary" style="margin:0 10px;">上传图片</el-button>
                            <template #tip>
                                <div class="el-upload__tip">
                                    jpg/png files with a size less than 500kb
                                </div>
                            </template>
                        </el-upload>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">提交</el-button>
                        <el-button>重置</el-button>
                    </el-form-item>
                </el-form>
            </el-card>
        </el-col>
        <el-col :span="12">
            <el-card style="width: 500px; padding: 20px;">
                <h4 class="h4_1">已提交的记录</h4>
                <div class="tag">
                    <el-tag class="ml-2" type="success">审核通过</el-tag>
                    <el-tag class="ml-2" type="info">未审核</el-tag>
                    <el-tag class="ml-2" type="danger">审核未通过</el-tag>
                </div>
                <el-timeline>
                    <el-timeline-item v-for="(activity, index) in activities" :key="index" :icon="activity.icon"
                        :type="activity.type" :color="activity.color" :size="activity.size" :hollow="activity.hollow"
                        :timestamp="activity.timestamp">
                        {{ activity.content }}
                    </el-timeline-item>
                </el-timeline>
            </el-card>
        </el-col>
    </el-row>
</template>
<script setup>
    import { onMounted, ref, reactive } from 'vue'
    import config from './../config'
    import { ElMessage } from 'element-plus'
    import { getCurrentInstance } from 'vue'
    let pform = ref({
        id: null,
        name: '',
        age: '',
        sex: '',
        birth: '',
        rate: '',
        img: ''
    })
    const activities = [
        {
            content: 'Custom icon',
            timestamp: '2018-04-12 20:46',
            size: 'large',
            type: 'primary',
        },
        {
            content: 'Custom color',
            timestamp: '2018-04-03 20:46',
            color: '#0bbd87',
        },
        {
            content: 'Custom size',
            timestamp: '2018-04-03 20:46',
            size: 'large',
        },
        {
            content: 'Custom hollow',
            timestamp: '2018-04-03 20:46',
            type: 'primary',
            hollow: true,
        },
        {
            content: 'Default node',
            timestamp: '2018-04-03 20:46',
        },
    ]
    const handleIMGsuccess = (res) => {
        pform.value.img = res.data
        ElMessage({
            message: '上传成功',
            type: 'success',
        })
    }

</script>
<style scoped>
    .h4_1 {
        text-align: center;
        font-size: 25px;
        margin-top: 0;
        margin-bottom: 10px;
        color: skyblue;
    }
    .tag {
        margin-bottom: 10px;
        display: flex;
        justify-content : flex-end
    }
    .el-tag {
        margin-left: 2px;
    }
</style>