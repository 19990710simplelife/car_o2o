/*
 * @Description: 登录相关的api
 * @Author: 执手天涯
 * @Date: 2023-03-30 18:47:04
 * @LastEditors: 执手天涯
 * @LastEditTime: 2023-03-30 19:02:04
 * @version: 1.0
 */
import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/admin/system/index/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/admin/system/index/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/admin/system/index/logout',
    method: 'post'
  })
}
