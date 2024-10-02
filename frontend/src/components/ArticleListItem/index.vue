<template>
  <div class="relative h-50 w-full bg-[#fff] p-2 flex gap-2">
    <div
      class="absolute top-4 left-[-6px] bg-[#2E52FD] text-white text-xs p-1 font-bold shadow-md z-10 cursor-pointer"
    >
      {{ data.category.categoryName }}
    </div>
    <!-- 封面 -->
    <div class="w-70">
      <a-image
        class="cursor-pointer"
        width="280"
        height="184"
        v-if="data.coverImageUrl"
        :src="data.coverImageUrl"
        show-loader
        :preview="false"
        @click="toArticleDetail(data)"
      />
    </div>
    <!--文章内容 -->
    <div class="relative flex-1">
      <h2
        class="font-bold overflow-hidden text-ellipsis break-words line-clamp-1 cursor-pointer"
        @click="toArticleDetail(data)"
      >
        {{ data.title }}
      </h2>
      <div
        class="mt-4 text-sm text-gray-500 indent-2em overflow-hidden text-ellipsis break-words line-clamp-4 cursor-pointer"
        @click="toArticleDetail(data)"
      >
        {{ data.summary }}
      </div>
      <div class="absolute w-full bottom-0 flex justify-between items-center">
        <a-space>
          <a-tag
            class="cursor-pointer"
            size="small"
            v-for="tag in data.tags"
            :key="tag.tagId"
            :color="tag.color"
          >
            {{ tag.tagName }}
          </a-tag>
        </a-space>

        <a-space class="text-xs text-gray-400">
          <span>{{ formatDate(data.publishTime, 'YYYY/MM/DD') }}</span>
        </a-space>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { PropType } from 'vue'
import { formatDate } from '@/utils/date'
import { useRouter } from 'vue-router'

defineProps({
  data: {
    type: Object as PropType<ApiArticle.QueryResponse>,
    required: true
  }
})

const router = useRouter()

const toArticleDetail = (data: ApiArticle.QueryResponse) => {
  router.push(`/article/${data.articleId}`)
}
</script>

<style scoped lang="scss"></style>
