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
    method: 'get'
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

export function getFoodList2() {
  return request({
    url: '/getFoodList',
    method: 'post'
  })
}

export function editFood(id, picture, name, describe, price) {
  return request({
    url: '/editFood',
    method: 'post',
    params: {
      id,
      picture,
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
