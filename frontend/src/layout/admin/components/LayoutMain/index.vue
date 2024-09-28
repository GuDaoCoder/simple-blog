<template>
  <div class="w-full h-full p-4 bg-gray-100">
    <router-view v-slot="{ Component, route }">
      <transition name="fade" mode="out-in" appear>
        <div class="w-full h-full p-4">
          <component :is="Component" :key="route.path" />
        </div>
      </transition>
    </router-view>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped></style>
