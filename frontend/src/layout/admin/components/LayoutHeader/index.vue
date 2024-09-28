<template>
  <div
    class="flex justify-between items-center h-full px-20px border-b border-solid border-gray-300"
  >
    <div class="flex gap-2">
      <IconApplication />
      <tiny-breadcrumb separator="/" :options="breadcrumboptions" />
    </div>
    <ul class="flex gap-2">
      <li>
        <tiny-user-head type="icon" round min></tiny-user-head>
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { iconApplication } from '@opentiny/vue-icon'
import { computed, ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const IconApplication = iconApplication()

const breadcrumboptions = computed(() =>
  route.matched.filter((o) => o.meta.title).map((o) => ({ label: o.meta.title }))
)
</script>

<style scoped lang="scss"></style>
