import type { RouteRaw } from '../type'
import { IconPublicHome } from '@opentiny/vue-icon'

const HOME: RouteRaw = {
  path: '/admin',
  name: 'admin',
  redirect: '/admin/home',
  component: () => import('@layout/admin/index.vue'),
  meta: {
    order: 1,
    title: '主页',
    icon: IconPublicHome()
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
