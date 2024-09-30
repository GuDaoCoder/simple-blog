<template>
  <tiny-container pattern="classic">
    <layout-main />
    <template #header>
      <layout-header />
    </template>
    <template #footer>
      <layout-footer />
    </template>
  </tiny-container>
</template>

<script setup lang="ts">
import LayoutHeader from './components/LayoutHeader/index.vue'
import LayoutMain from './components/LayoutMain/index.vue'
import LayoutFooter from './components/LayoutFooter/index.vue'
</script>

<style scoped></style>
