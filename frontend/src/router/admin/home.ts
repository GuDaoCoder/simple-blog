import type { RouteRaw } from '../type'

const HOME: RouteRaw = {
  path: '/admin',
  name: 'admin',
  redirect: '/admin/home',
  component: () => import('@layout/admin/index.vue'),
  children: [
    {
      name: 'admin-home',
      path: '/admin/home',
      component: () => import('@/views/admin/home/index.vue'),
      meta: {
        icon: 'home',
        title: '主页'
      }
    }
  ]
}

export default HOME
