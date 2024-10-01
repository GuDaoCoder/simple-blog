<template>
  <div
    class="flex justify-between items-center h-full px-20px border-b border-solid border-gray-300"
  >
    <div class="flex gap-2">
      <a-breadcrumb :routes="breadcrumbOptions" />
    </div>
    <ul class="flex gap-2">
      <li>
        <a-avatar
          imageUrl="https://p1-arco.byteimg.com/tos-cn-i-uwbnlip3yd/3ee5f13fb09879ecb5185e440cef6eb9.png~tplv-uwbnlip3yd-webp.webp"
        />
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
const route = useRoute()

const breadcrumbOptions = computed(() =>
  route.matched
    .filter((o) => o.meta.title)
    .map((o) => ({
      path: o.path,
      label: o.meta.title
    }))
)
</script>

<style scoped lang="scss"></style>
