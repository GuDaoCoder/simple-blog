<template>
  <div class="h-full border-solid border-r border-gray-300">
    <div class="w-full h-60px flex items-center justify-center">
      <h2>simple-blog</h2>
    </div>
    <tiny-tree-menu
      style="width: 200px"
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
import { ref } from 'vue'
import { IconPublicHome, IconDocument, IconSetting } from '@opentiny/vue-icon'
import { useRouter } from 'vue-router'

const router = useRouter()

const menuData = ref([
  {
    id: 1,
    path: '/admin/home',
    label: '首页',
    customIcon: IconPublicHome()
  },
  {
    id: 2,
    label: '博客管理',
    customIcon: IconDocument(),
    children: [
      {
        id: 21,
        path: '/admin/article',
        label: '文章管理'
      },
      {
        id: 22,
        path: '/admin/category',
        label: '分类管理'
      },
      {
        id: 23,
        path: '/admin/tag',
        label: '标签管理'
      }
    ]
  },
  {
    id: 3,
    label: '系统管理',
    customIcon: IconSetting()
  }
])

const handleNodeClick = (node: any) => {
  if (!node.children && node.path) {
    router.push({ path: node.path })
  }
}
</script>

<style scoped lang="scss"></style>
