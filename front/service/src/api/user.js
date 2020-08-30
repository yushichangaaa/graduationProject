import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function getInfo(user) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { user }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

export function regist(username, password, phone) {
  return request({
    url: '/user/regist',
    method: 'post',
    params: {
      username,
      password,
      phone
    }
  })
}
