<template>
  <div class="h-full border-solid border-r-1px">
    <div class="w-full h-60px flex items-center justify-center">
      <h2>simple-blog</h2>
    </div>
    <tiny-tree-menu
      style="width: 199px"
      :data="menuData"
      :show-filter="false"
      accordion
      wrap
      collapsible
      @node-click="handleNodeClick"
    ></tiny-tree-menu>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRouter, type RouteRecordRaw } from 'vue-router'
import { adminRoutes } from '@/router/routes'

const router = useRouter()

const transformRoutes = (routes: RouteRecordRaw[]): TinyVue.ITreeNodeData[] => {
  return routes
    .filter((route) => route.meta?.title)
    .sort((a, b) => {
      const orderA = a.meta?.order ?? Infinity
      const orderB = b.meta?.order ?? Infinity
      return orderA - orderB
    })
    .map((route) => {
      const menuItem: TinyVue.ITreeNodeData = {
        id: route.path,
        label: route.meta?.title || '',
        path: route.path,
        customIcon: route.meta?.icon
      }

      // 如果有子路由，递归处理
      if (route.children && route.children.length > 0) {
        menuItem.children = transformRoutes(route.children)
      }
      return menuItem
    })
}

const menuData = computed(() => transformRoutes(adminRoutes))

const handleNodeClick = (node: any) => {
  if (node.children && node.children.length > 0) {
    return
  }
  if (node.path) {
    router.push({ path: node.path })
  }
}
</script>

<style scoped lang="scss"></style>
