// 整个项目api的管理

import request from "./request";
export default {
    getPieData(params) {
        return request({
            url: '/echarts/pie',
            method: 'get',
            data: params,
            mock: true
        })
    },    
    getPeoList(params) {
        return request({
            url: '/people/findAll',
            method: 'get',
            data: params,
            mock: true
        })
    },
    savePeo(params) {
        return request({
            url: '/people/save',
            method: 'post',
            data: params,
            mock: true
        })
    },
    deletePeo(params) {
        return request({
            url: '/people/delete',
            method: 'delete',
            data: params,
            mock: true
        })
    },
    batchDel(params) {
        return request({
            url: '/people/batchDel',
            method: 'delete',
            data: params,
            mock: true
        })
    },
    userLogin(params) {
        return request({
            url: '/user/login',
            method: 'post',
            data: params,
            mock: true
        })
    },
    getIUserInfo(params) {
        return request({
            url: '/user/index',
            method: 'get',
            data: params,
            mock: true
        })
    },
    commitRate(params) {
        return request({
            url: '/rate/commit',
            method: 'post',
            data: params,
            mock: true
        })
    },
    getRateInfo(params) {
        return request({
            url: '/rate/getRateInfo',
            method: 'get',
            data: params,
            mock: true
        })
    }
}