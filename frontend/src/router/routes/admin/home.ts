import type { AppRouteRecordRaw } from '../types'

const HOME: AppRouteRecordRaw = {
  path: '/admin',
  name: 'admin',
  redirect: '/admin/home',
  component: () => import('@layout/admin/index.vue'),
  meta: {
    order: 0,
    title: '主页',
    hideChildrenInMenu: true
  },
  children: [
    {
      name: 'admin-home',
      path: '/admin/home',
      component: () => import('@/views/admin/home/index.vue')
    }
  ]
}

export default HOME
