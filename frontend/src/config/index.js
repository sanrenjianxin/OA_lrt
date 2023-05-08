// 环境配置文件
// 一般企业级项目中有三种环境
// 开发环境
// 测试环境
// 线上环境

// 当前的环境 如果取不到的话默认为线上环境prod
const env = import.meta.env.MODE || 'prod'

const EnvConfig = {
    development: {
        baseApi: '',
        mockApi: 'http://localhost:8080/',
    },
    test: {
        baseApi: '',
        mockApi: 'http://localhost:8080/',
    },
    prod: {
        baseApi: '//future.com/api',
        mockApi: 'https://www.fastmock.site/mock/2813bf740c0964d8e2d621f759a85982/api',
    },
    
}

export default {
    env,
    // mock的总开关
    mock: true,
    ...EnvConfig[env]
}