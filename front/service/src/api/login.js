import request from '@/utils/request'

var user = ''
export function login(username, password) {
  user = username
  return request({
    url: '/user/login',
    method: 'post',
    params: {
      username,
      password
    }
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: {
      token,
      user
    }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

export function sendMsg() {
  return request({
    url: '/user/sendMsg',
    method: 'post'
  })
}

export function activeUser(username) {
  return request({
    url: '/user/active',
    method: 'post',
    params: {
      username
    }
  })
}
