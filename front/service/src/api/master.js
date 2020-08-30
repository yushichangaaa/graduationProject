import request from '@/utils/request'

export function getComment() {
  return request({
    url: '/getComment', // 这个接口是为了服务端算一共有多少留言数的   客户端还有一个查询特定排序版的getComments
    method: 'post'
  })
}

export function getFlow() {
  return request({
    url: '/get7DaysFlow',
    method: 'post'
  })
}

export function getFoodList(page, rows) {
  return request({
    url: '/getFoodList',
    method: 'post',
    params: {
      page,
      rows
    }
  })
}

export function editFood(id, name, describe, price) {
  return request({
    url: '/editFood',
    method: 'post',
    params: {
      id,
      name,
      describe,
      price
    }
  })
}

export function deleteFood(id) {
  return request({
    url: '/deleteFood',
    method: 'post',
    params: {
      id
    }
  })
}
