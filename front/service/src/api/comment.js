import request from '@/utils/request'

export function comment(name, text) {
  return request({
    url: '/comment',
    method: 'post',
    params: {
      name,
      text
    }
  })
}

export function getComments() {
  return request({
    url: '/getComments',
    methods: 'post'
  })
}

export function rp(parent, name, text) {
  return request({
    url: '/comment',
    method: 'post',
    params: {
      parent,
      name,
      text
    }
  })
}
