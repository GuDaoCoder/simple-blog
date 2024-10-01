<template>
  <a-layout id="main">
    <a-affix :offsetTop="0">
      <a-layout-header class="bg-white">
        <layout-header class="h-15 shadow-md" />
      </a-layout-header>
    </a-affix>
    <a-layout-content class="bg-gray-100">
      <layout-main />
    </a-layout-content>
    <a-layout-footer class="bg-white w-full">
      <layout-footer />
    </a-layout-footer>
  </a-layout>

  <a-back-top style="bottom: 80px; right: 40px" />
</template>

<script setup lang="ts">
import LayoutHeader from './components/LayoutHeader/index.vue'
import LayoutMain from './components/LayoutMain/index.vue'
import LayoutFooter from './components/LayoutFooter/index.vue'
</script>

<style scoped lang="scss"></style>
