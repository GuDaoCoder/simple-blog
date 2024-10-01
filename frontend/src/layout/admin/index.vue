<template>
  <a-layout>
    <a-layout-sider>
      <layout-aside />
    </a-layout-sider>
    <a-layout>
      <a-layout-header class="h-15">
        <layout-header />
      </a-layout-header>
      <a-layout>
        <a-layout-content>
          <layout-main />
        </a-layout-content>
        <a-layout-footer class="h-15">
          <layout-footer />
        </a-layout-footer>
      </a-layout>
    </a-layout>
  </a-layout>
</template>

<script setup lang="ts">
import LayoutMain from './components/LayoutMain/index.vue'
import LayoutHeader from './components/LayoutHeader/index.vue'
import LayoutAside from './components/LayoutAside/index.vue'
import LayoutFooter from './components/LayoutFooter/index.vue'
</script>

<style scoped lang="scss"></style>
