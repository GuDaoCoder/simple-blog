<template>
  <div>
    <tiny-container pattern="fashion">
      <layout-main />
      <template #header>
        <layout-header />
      </template>
      <template #aside>
        <layout-aside />
      </template>
      <!-- <template #footer>
        <layout-footer />
      </template> -->
    </tiny-container>
  </div>
</template>

<script setup lang="ts">
import LayoutMain from './components/LayoutMain/index.vue'
import LayoutHeader from './components/LayoutHeader/index.vue'
import LayoutAside from './components/LayoutAside/index.vue'
</script>

<style scoped lang="scss"></style>
