<template>
  <a-menu
    level-indent.number="40"
    @menu-item-click="handleClickMenuItem"
    auto-open-selected
    :selected-keys="selectedKeys"
  >
    <menu-item :data="menuData" />
  </a-menu>
</template>

<script setup lang="ts">
import MenuItem from '@/components/MenuItem/index.vue'
import { type RouteRecordRaw } from 'vue-router'
import { adminRoutes } from '@/router/routes'

const router = useRouter()

const transformRoutes = (routes: RouteRecordRaw[]) => {
  return routes
    .filter((route) => route.meta?.title)
    .sort((a: RouteRecordRaw, b: RouteRecordRaw) => {
      return (a.meta?.order || 0) - (b.meta?.order || 0)
    })
    .map((route) => {
      const menuItem: Menu = {
        name: route.name,
        path: route.path,
        title: route.meta?.title || '',
        icon: route.meta?.icon
      }

      // 如果有子路由，递归处理
      if (!route.meta?.hideChildrenInMenu && route.children) {
        menuItem.children = transformRoutes(route.children)
      }
      return menuItem
    })
}

const menuData = computed(() => transformRoutes(adminRoutes))

const handleClickMenuItem = (name: string) => {
  router.push({ name })
}

const selectedKeys = computed(() => {
  let selectKey = router.currentRoute.value.name
  if (selectKey === 'admin-home') {
    return ['admin']
  }
  return [selectKey]
})
</script>

<style scoped></style>
