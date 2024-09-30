<template>
  <div class="flex py-4 mx-auto w-1000px">
    <div class="w-200px space-y-5">
      <layout-asider />
    </div>
    <div class="flex-1 ml-4">
      <router-view v-slot="{ Component, route }">
        <transition name="fade" mode="out-in" appear>
          <component :is="Component" :key="route.path" />
        </transition>
      </router-view>
    </div>
  </div>
</template>

<script setup lang="ts">
import LayoutAsider from '../LayoutAsider/index.vue'
</script>

<style scoped></style>
