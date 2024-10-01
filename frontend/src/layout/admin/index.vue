<template>
  <a-layout>
    <a-layout-sider class="h-screen">
      <layout-aside />
    </a-layout-sider>
    <a-layout>
      <a-layout-header class="h-60px">
        <layout-header />
      </a-layout-header>
      <a-layout>
        <a-layout-content>
          <layout-main />
        </a-layout-content>
        <a-layout-footer>Footer</a-layout-footer>
      </a-layout>
    </a-layout>
  </a-layout>
</template>

<script setup lang="ts">
import LayoutMain from './components/LayoutMain/index.vue'
import LayoutHeader from './components/LayoutHeader/index.vue'
import LayoutAside from './components/LayoutAside/index.vue'
</script>

<style scoped lang="scss"></style>
