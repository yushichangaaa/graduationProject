import request from '@/utils/request'

export function sendMsg() {
  return request({
    url: '/send',
    method: 'post'
  })
}
