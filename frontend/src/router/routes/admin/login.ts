import type { AppRouteRecordRaw } from '../types'

const LOGIN: AppRouteRecordRaw = {
  path: '/admin/login',
  name: 'admin-login',
  component: () => import('@/views/admin/login/index.vue')
}

export default LOGIN
