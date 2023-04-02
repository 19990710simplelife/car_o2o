import request from '@/utils/request'

const api_name = '/admin/system/sysUser'

export default {

  /**
     * 分页获取用户信息
     * @param {当前页} page
     * @param {每页显示条数} limit
     * @param {查询条件} searchObj
     */
  getPageList(page, limit, searchObj) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'get',
      params: searchObj
    })
  },

  /**
     * 根据用户id删除用户信息
     * @param {用户id} id
     * @returns
     */
  removeById(id) {
    return request({
      url: `${api_name}/remove/${id}`,
      method: 'delete'
    })
  },

  /**
     * 添加用户信息
     * @param {用户信息} role
     * @returns
     */
  save(role) {
    return request({
      url: `${api_name}/save`,
      method: 'post',
      data: role
    })
  },

  /**
     * 根据id获取用户信息
     * @param {用户对象id} id
     * @returns
     */
  getById(id) {
    return request({
      url: `${api_name}/get/${id}`,
      method: 'get'
    })
  },

  /**
     * 修改用户信息
     * @param {用户信息} role
     * @returns
     */
  updateById(role) {
    return request({
      url: `${api_name}/update`,
      method: 'put',
      data: role
    })
  }
}
