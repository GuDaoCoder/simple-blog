<template>
  <div class="w-full h-full bg-white">
    <p
      class="text-[24px] text-[#333] font-bold text-center pt-[40px] break-words shadow-[0_1px_2px_rgba(0,0,0,0.25)]"
    >
      {{ article.title }}
    </p>
    <a-space class="text-[16px] px-10 py-5 text-[#909399]">
      <span>{{ formatDate(article.publishTime, 'YYYY/MM/DD') }}</span>
    </a-space>

    <a-divider />

    <v-md-preview :text="articleContent" @copy-code-success="handleCopyCodeSuccess"></v-md-preview>

    <a-divider />

    <div class="flex justify-between items-center px-10 py-4">
      <a-space>
        <span class="font-bold">标签：</span>
        <a-tag v-for="tag in article.tags" :key="tag.tagId" :color="tag.color">
          {{ tag.tagName }}
        </a-tag>
      </a-space>

      <a-space>
        <a-button type="primary">
          <template #icon><icon-heart-fill /></template>
        </a-button>
        <a-button type="primary">
          <template #icon><icon-thumb-up-fill /></template>
        </a-button>
      </a-space>
    </div>
  </div>
</template>

<script setup lang="ts">
import { portalGetArticle, portalArticleContent } from '@/api/article'
import { formatDate } from '@/utils/date'
import { Notification } from '@arco-design/web-vue'

const route = useRoute()

const article = ref<ApiArticle.QueryResponse>(<ApiArticle.QueryResponse>{})
const articleContent = ref('')

onMounted(() => {
  const articleId = route.params.articleId as unknown as number
  getPortalArticle(articleId)
  getPortalArticleContent(articleId)
})

const getPortalArticle = (articleId: number) => {
  portalGetArticle(articleId).then((res) => {
    article.value = res
  })
}

/**
 * 获取文章详情
 * @param articleId
 */
const getPortalArticleContent = (articleId: number) => {
  portalArticleContent(articleId).then((res) => {
    articleContent.value = res
  })
}

const handleCopyCodeSuccess = () => {
  Notification.success('复制成功')
}
</script>
<style scoped></style>
