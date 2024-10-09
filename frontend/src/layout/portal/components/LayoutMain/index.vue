<template>
  <div class="flex py-4 mx-auto w-1200px">
    <div class="w-240px space-y-5">
      <layout-asider />
    </div>
    <div class="flex-1 ml-4" style="min-width: 0">
      <router-view v-slot="{ Component, route }">
        <transition name="fade" mode="out-in" appear>
          <div :key="route.path">
            <component :is="Component" />
          </div>
        </transition>
      </router-view>
    </div>
  </div>
</template>

<script setup lang="ts">
import LayoutAsider from '../LayoutAsider/index.vue'
</script>

<style scoped></style>
