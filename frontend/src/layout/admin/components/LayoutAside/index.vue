<template>
  <div>
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
import { IconDocument, IconSetting } from '@opentiny/vue-icon'
import { useRouter } from 'vue-router'

const router = useRouter()

const menuData = ref([
  {
    id: 1,
    label: '博客管理',
    customIcon: IconDocument(),
    children: [
      {
        id: 11,
        code: 'admin-article',
        label: '文章管理'
      },
      {
        id: 12,
        label: '分类管理'
      },
      {
        id: 13,
        label: '标签管理'
      }
    ]
  },
  {
    id: 2,
    label: '系统管理',
    customIcon: IconSetting()
  }
])

const handleNodeClick = (node: any) => {
  if (!node.children && node.code) {
    router.push({ name: node.code })
  }
}
</script>

<style scoped lang="scss">
:deep(.tiny-tree-menu) {
  --ti-tree-menu-border-color: #fff;
}
.menu {
  width: 200px;
}
</style>
