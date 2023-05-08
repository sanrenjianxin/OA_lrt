<script>
import { RouterLink, RouterView } from 'vue-router'

import { ElConfigProvider } from 'element-plus'
import zhCn from 'element-plus/lib/locale/lang/zh-cn'

export default {
  setup() {
    return {
      locale: zhCn
    }
  }
}

</script>

<template>
  <el-config-provider :locale="locale">
  <RouterView />
  </el-config-provider>
</template>

<style>
</style>
