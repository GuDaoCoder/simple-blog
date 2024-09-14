import type { RouteRaw } from '../type'

const BLOG: RouteRaw = {
  path: '/blog',
  name: 'blog',
  redirect: '/admin/article',
  component: () => import('@layout/admin/index.vue'),
  meta: {
    title: '博客管理'
  },
  children: [
    {
      name: 'admin-article',
      path: '/admin/article',
      component: () => import('@/views/admin/article/index.vue'),
      meta: {
        title: '文章管理'
      }
    }
  ]
}

export default BLOG
