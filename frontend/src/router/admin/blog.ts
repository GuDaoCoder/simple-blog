import type { RouteRaw } from '../type'
import { IconDocument } from '@opentiny/vue-icon'

const BLOG: RouteRaw = {
  path: '/blog',
  name: 'blog',
  redirect: '/admin/article',
  component: () => import('@layout/admin/index.vue'),
  meta: {
    order: 2,
    title: '博客管理',
    icon: IconDocument()
  },
  children: [
    {
      name: 'admin-article',
      path: '/admin/article',
      component: () => import('@views/admin/article/index.vue'),
      meta: {
        title: '文章管理'
      }
    },
    {
      name: 'admin-category',
      path: '/admin/category',
      component: () => import('@views/admin/category/index.vue'),
      meta: {
        title: '分类管理'
      }
    },
    {
      name: 'admin-tag',
      path: '/admin/tag',
      component: () => import('@views/admin/tag/index.vue'),
      meta: {
        title: '标签管理'
      }
    }
  ]
}

export default BLOG
