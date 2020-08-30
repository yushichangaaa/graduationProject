import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in subMenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if false, the item will hidden in breadcrumb(default is true)
  }
**/
export const constantRouterMap = [
  { path: '/404', component: () => import('@/views/404'), hidden: true },
  { path: '/activate', component: () => import('@/views/activate/index'), hidden: true },
  { path: '/activeSuccess', component: () => import('@/views/activate/success'), hidden: true },
  { path: '/activeFail', component: () => import('@/views/activate/fail'), hidden: true },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index')
    }]
  },

  {
    path: '/aaa',
    component: Layout,
    redirect: '/aaa/comment',
    name: 'aaa',
    meta: { title: 'aaa', icon: 'example' },
    children: [
      {
        path: 'comment',
        name: 'comment',
        component: () => import('@/views/table/index'),
        meta: { title: '评论', icon: 'table' }
      },
      {
        path: 'user',
        name: 'user',
        component: () => import('@/views/user/index'),
        meta: { title: '用户', icon: 'tree' }
      },
      {
        path: 'dish',
        name: 'dish',
        component: () => import('@/views/dish/index'),
        meta: { title: '菜品', icon: 'tree' }
      },
      {
        path: 'shoppingCar',
        name: 'shoppingCar',
        component: () => import('@/views/shoppingCar/index'),
        meta: { title: '购物车', icon: 'tree' }
      }
    ]
  },

  {
    path: '/order',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'order',
        component: () => import('@/views/order/index'),
        meta: { title: '订单', icon: 'form' }
      }
    ]
  },

  {
    path: '/adress',
    component: Layout,
    redirect: '/nested',
    name: 'adress',
    meta: {
      title: 'adress',
      icon: 'nested'
    },
    children: [
      {
        path: '',
        component: () => import('@/views/adress/index'), // Parent router-view
        name: 'adress',
        meta: { title: '地址' }
      }
    ]
  },
  {
    path: '/coupon',
    component: Layout,
    children: [
      {
        path: '',
        name: 'coupon',
        component: () => import('@/views/coupon/index'),
        meta: { title: '优惠券', icon: 'form' }
      }
    ]
  },
  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  mode: 'history', // 后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
