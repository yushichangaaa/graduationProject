import router from './router'
import store from './store'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { Message } from 'element-ui'
import { getToken } from '@/utils/auth' // getToken from cookie
import { getActivate } from '@/utils/auth'

NProgress.configure({ showSpinner: false })// NProgress configuration

const whiteList = ['/login'] // 不重定向白名单
router.beforeEach((to, from, next) => {
  NProgress.start()
  console.log(getToken())
  console.log(to.path)
  console.log(to.path === '/login' && getActivate() === '1')
  if (getToken()) {
    if (to.path === '/login' && getActivate() === '1') {
      console.log(1)
      next({ path: '/' })
      NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
    } else {
      console.log(2)
      console.log(to.path !== '/activate')
      if (store.getters.roles.length === 0 && to.path !== '/activate') {
        console.log(3)
        if (getActivate() === '0') {
          console.log(4)
          next({ path: '/activate' })
          NProgress.done()
        } else {
          console.log(5)
          store.dispatch('GetInfo').then(res => { // 拉取用户信息
            next()
          }).catch((err) => {
            store.dispatch('FedLogOut').then(() => {
              Message.error(err || 'Verification failed, please login again')
              next({ path: '/' })
            })
          })
        }
      } else {
        console.log(6)
        next()
      }
    }
  } else {
    console.log(3333)
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next(`/login?redirect=${to.path}`) // 否则全部重定向到登录页
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done() // 结束Progress
})
