import axios from "axios";
import config from "../config"
import { ElMessage } from "element-plus";
import store from '../stores'
import { useRouter } from "vue-router"

const router = useRouter()
// 创建一个axios实例对象
const service = axios.create({
    baseURL: config.baseApi
})

// 在请求之前做一些事情
// TODO 拦截器请求发送token

// 添加请求拦截器
service.interceptors.request.use(function (config) {
    if (store.state.token) {
      config.headers.token = store.state.token
    }
    return config;
  }, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  });
  

// 在请求之后做一些事情

service.interceptors.response.use(response => {
    // 访问权限异常
    if (response.data.code === '401') {
        ElMessage({
            message: response.data.msg,
            type: 'error'
        })
        router.push({name: "403"})
    }
    return response
})


// 封装的核心函数
function request(options) {
    // 如果没有指定请求类型，则默认是get
    options.method = options.method || 'get'
    if (options.method.toLowerCase() == 'get'){
        options.params = options.data
    }
    // 对mock的处理
    // 单个接口可选择是否开启mock
    let isMock = config.mock
    if (typeof options.mock !== "undefined") {
        isMock = options.mock
    }
    // 对线上环境做处理
    if (config.env == "prod") {
        // 不给用到mock的机会
        service.defaults.baseURL = config.baseApi
    } else {
        service.defaults.baseURL = isMock ? config.mockApi : config.baseApi
    }

    return service(options)
}

export default request