<template>
  <div class="asider">
    <div class="logo-contaner">
      <h2>simple-blog</h2>
    </div>
    <tiny-tree-menu
      class="menu"
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

<style scoped lang="scss">
.asider {
  height: 100%;
  border-right: 1px solid #d8dae2;
}
.logo-contaner {
  width: 100%;
  height: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.menu {
  margin-top: 5px;
  width: 200px;
  background-color: #142031;
}
</style>
