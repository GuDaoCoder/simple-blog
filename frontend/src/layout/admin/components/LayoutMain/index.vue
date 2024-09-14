<template>
  <div class="main">
    <router-view v-slot="{ Component, route }">
      <transition name="fade" mode="out-in" appear>
        <div class="content">
          <component :is="Component" :key="route.path" />
        </div>
      </transition>
    </router-view>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.main {
  width: 100%;
  height: 100%;
  padding: 20px;
  background-color: #f4f5f6;
}

.content {
  width: 100%;
  height: 100%;
  padding: 20px;
  background-color: #ffffff;
}
</style>
