<template>
    <div class="common-layout">
        <el-container>
            <!-- 根据是否侧边栏是否折叠改变侧边栏的宽度 -->
            <el-aside :width="$store.state.isCollapse ? '64px' : '180px'"> 
                <common-aside />
            </el-aside>
            <el-container>
                <el-header>
                    <common-header />
                </el-header>
                <el-main>
                    <router-view />
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>


<script>
    import {defineComponent} from 'vue'
    import CommonHeader from '../components/CommonHeader.vue'
    import CommonAside from '../components/CommonAside.vue'
    export default defineComponent({
        components: {
            CommonHeader,
            CommonAside
        },
    })
</script>

<style>
    
  html,
  body,
  #app,
  .common-layout,
  .el-container {
    padding: 0px;
    margin: 0px;
    height: 100%;
  }

  .el-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    background-color: #333;
}

  h3 {
    font-weight: normal;
    margin-block-start: 0;
    margin-block-end: 0;
  }
  .el-aside {
    background-color: #545c64 
  }
</style>