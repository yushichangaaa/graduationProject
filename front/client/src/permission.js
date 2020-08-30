import router from './router'
import { sendMsg } from '@/api/mq'

router.beforeEach((to, from, next) => {
  sendMsg().then(() => {
  }, () => {
  })
  next()
})

