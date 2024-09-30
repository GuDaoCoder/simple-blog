<template>
  <div class="flex px-32 py-4">
    <div class="w-200px bg-red-600"></div>
    <div class="flex-1 ml-4">
      <router-view v-slot="{ Component, route }">
        <transition name="fade" mode="out-in" appear>
          <component :is="Component" :key="route.path" />
        </transition>
      </router-view>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped></style>
