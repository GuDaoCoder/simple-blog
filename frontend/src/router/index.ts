import { createRouter, createWebHistory } from 'vue-router'
import { adminRoutes, portalRoutes } from './routes'
import { getToken } from '@utils/auth'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [...adminRoutes, ...portalRoutes]
})

const whiteUrls = ['/', '/admin/login', '/home', '/article']
/**
 * 导航守卫
 */
router.beforeEach((to, from, next) => {
  if (whiteUrls.indexOf(to.path) != -1) {
    next()
  } else {
    if (getToken()) {
      next()
    } else {
      next({ name: 'admin-login' })
    }
  }
})

export default router
