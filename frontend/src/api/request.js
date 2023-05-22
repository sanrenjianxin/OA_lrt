import axios from "axios";
import config from "../config"
import { ElMessage } from "element-plus";
import store from '../stores'

const NETWORK_ERROR = '网络请求异常，请稍后重试......'
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

// service.interceptors.response.use(function (response) {
//     // 2xx 范围内的状态码都会触发该函数。
//     // 对响应数据做点什么
//     return response;
//   }, function (error) {
//     // 超出 2xx 范围的状态码都会触发该函数。
//     // 对响应错误做点什么
//     if (error.response.status === 401) {
//       alert('请先登录')
//     }
//     return Promise.reject(error);
//   });


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