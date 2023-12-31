import request from '@/utils/request'

// 查询店铺订单列表
export function listOrder(query) {
  return request({
    url: '/shop/order/list',
    method: 'get',
    params: query
  })
}

// 查询店铺订单详细
export function getOrder(id) {
  return request({
    url: '/shop/order/' + id,
    method: 'get'
  })
}

// 新增店铺订单
export function addOrder(data) {
  return request({
    url: '/shop/order',
    method: 'post',
    data: data
  })
}

// 修改店铺订单
export function updateOrder(data) {
  return request({
    url: '/shop/order',
    method: 'put',
    data: data
  })
}

// 删除店铺订单
export function delOrder(id) {
  return request({
    url: '/shop/order/' + id,
    method: 'delete'
  })
}
