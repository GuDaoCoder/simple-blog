<template>
  <a-card :bordered="false" hoverable title="文章分类">
    <a-tree
      :data="categoryTreeData"
      :field-names="{ key: 'categoryId', title: 'categoryName' }"
      show-line
    />
  </a-card>
</template>

<script setup lang="ts">
import { portalTreeCategories } from '@/api/category'
import { onMounted } from 'vue'

onMounted(() => {
  handleFetchCategoryTree()
})

const categoryTreeData = ref<ApiCategory.QueryResponse[]>([])
const handleFetchCategoryTree = async () => {
  portalTreeCategories().then((res) => {
    categoryTreeData.value = res
  })
}
</script>

<style scoped></style>
