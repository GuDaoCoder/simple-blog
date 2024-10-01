<template>
  <div class="space-y-2">
    <article-list-item v-for="article in articles" :key="article.articleId" :data="article" />
  </div>
  <a-pagination
    class="mt-4 justify-end"
    :current="pagination.pageNumber"
    :page-size="pagination.pageSize"
    :total="pagination.total"
    @change="handleChangePageNumber"
    @page-size-change="handleChangePageSize"
  />
</template>

<script setup lang="ts">
import type { PropType } from 'vue'
import { portalQueryArticles } from '@/api/article'
import { onMounted } from 'vue'

const props = defineProps({
  form: {
    type: Object as PropType<ApiArticle.PortalQueryForm>,
    default() {
      return {
        title: '',
        categoryId: null
      }
    }
  }
})

const pagination = ref<Pagination>({
  pageNumber: 1,
  pageSize: 10,
  total: 0
})

const articles = ref<ApiArticle.QueryResponse[]>([])

onMounted(() => {
  handleFetchArticles()
})

const handleFetchArticles = () => {
  let params = {
    pageNumber: pagination.value.pageNumber,
    pageSize: pagination.value.pageSize,
    title: props.form.title,
    categoryId: props.form.categoryId
  }
  portalQueryArticles(params).then((res) => {
    articles.value = res.items
    pagination.value.total = res.total
  })
}

const handleChangePageNumber = (pageNumber: number) => {
  pagination.value.pageNumber = pageNumber
  handleFetchArticles()
}

const handleChangePageSize = (pageSize: number) => {
  pagination.value.pageSize = pageSize
  handleFetchArticles()
}
</script>

<style scoped></style>
