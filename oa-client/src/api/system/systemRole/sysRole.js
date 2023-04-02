import request from '@/utils/request'

const api_name = '/admin/system/sysRole'

export default {
  /**
       * 分页获取角色信息
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
   * 根据角色id删除角色信息
   * @param {角色id} id
   * @returns
   */
  removeById(id) {
    return request({
      url: `${api_name}/remove/${id}`,
      method: 'delete'
    })
  },

  /**
   * 添加角色信息
   * @param {角色信息} role
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
   * 根据id获取角色信息
   * @param {角色对象id} id
   * @returns
   */
  getById(id) {
    return request({
      url: `${api_name}/get/${id}`,
      method: 'get'
    })
  },

  /**
   * 修改角色信息
   * @param {角色信息} role
   * @returns
   */
  updateById(role) {
    return request({
      url: `${api_name}/update`,
      method: 'put',
      data: role
    })
  },
  /**
   * 批量删除角色信息
   * @param {角色id数组} idList
   * @returns
   */
  batchRemove(idList) {
    return request({
      url: `${api_name}/batchRemove`,
      method: `delete`,
      data: idList
    })
  }
}
