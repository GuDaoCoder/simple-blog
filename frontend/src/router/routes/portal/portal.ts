import type { AppRouteRecordRaw } from '../types'

const PORTAL: AppRouteRecordRaw = {
  path: '/',
  name: 'portal',
  component: () => import('@layout/portal/index.vue'),
  redirect: '/home',
  children: [
    {
      name: 'home',
      path: '/home',
      component: () => import('@/views/portal/home/index.vue')
    }
  ]
}

export default PORTAL
