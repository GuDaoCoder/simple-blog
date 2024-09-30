<template>
  <div class="flex justify-between items-center px-4">
    <div class="w-[50%]">
      <tiny-nav-menu :data="menuData"></tiny-nav-menu>
    </div>
    <ul class="flex gap-2">
      <tiny-input placeholder="搜索" clearable size="small">
        <template #prefix>
          <tiny-icon-search />
        </template>
      </tiny-input>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { iconSearch } from '@opentiny/vue-icon'
import { ref } from 'vue'

const TinyIconSearch = iconSearch()

const menuData = ref([
  {
    title: '首页',
    url: ''
  },
  {
    title: '分类',
    url: '',
    children: [
      {
        title: '后端开发',
        url: '',
        children: [
          {
            title: 'Java',
            url: ''
          },
          {
            title: 'Springboot',
            url: ''
          }
        ]
      }
    ]
  }
])
</script>

<style scoped lang="scss">
:deep(.tiny-nav-menu) {
  box-shadow: none;
}
</style>
