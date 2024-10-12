<template>
  <a-card :bordered="false" hoverable title="目录">
    <a-anchor style="width: 200px" boundary="center">
      <a-anchor-link
        :href="`#data-v-md-line-${anchor.lineIndex}`"
        v-for="anchor in anchors"
        :key="anchor.lineIndex"
      >
        {{ anchor.title }}
        <template #sublist v-if="anchor.children">
          <a-anchor-link
            :href="`#data-v-md-line-${childAnchor.lineIndex}`"
            v-for="childAnchor in anchor.children"
            :key="childAnchor.lineIndex"
            >{{ childAnchor.title }}
          </a-anchor-link>
        </template>
      </a-anchor-link>
    </a-anchor>
  </a-card>
</template>

<script setup lang="ts">
import useAnchorStore from '@/store/modules/anchor'

const useAnchor = useAnchorStore()
const anchors = ref<AnchorState[]>([])

onMounted(() => {
  anchors.value = useAnchor.$state
})
</script>

<style scoped></style>
