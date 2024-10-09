<template>
  <a-card :bordered="false" hoverable title="标签">
    <div class="flex flex-wrap gap-[5px]">
      <a-tag v-for="tag in tagData" :key="tag.tagId" :color="tag.color" class="tag pointer"
        >{{ tag.tagName }}
      </a-tag>
    </div>
  </a-card>
</template>

<script setup lang="ts">
import { portalQueryAllTags } from '@/api/tag'
import { onMounted } from 'vue'

onMounted(() => {
  handleFetchTags()
})

const tagData = ref<ApiTag.QueryResponse[]>([])
const handleFetchTags = async () => {
  portalQueryAllTags().then((res) => {
    tagData.value = res
  })
}
</script>

<style scoped></style>
