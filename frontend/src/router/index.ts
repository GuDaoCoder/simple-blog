import { createRouter, createWebHistory } from 'vue-router'
import { adminRoutes, portalRoutes } from './routes'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [...adminRoutes, ...portalRoutes]
})

export default router
