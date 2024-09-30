import type { RouteRaw } from '../type'

const PORTAL: RouteRaw = {
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
