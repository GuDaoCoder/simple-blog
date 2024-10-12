<template>
  <a-affix :offsetTop="70">
    <a-space direction="vertical" class="w-full">
      <user-card />
      <transition name="slide-up">
        <anchor-card v-if="showAnchorCard" />
      </transition>
      <category-card />
      <tag-card />
    </a-space>
  </a-affix>
</template>

<script setup lang="ts">
const route = useRoute()
const showAnchorCard = computed(() => {
  return route.name === 'article'
})
</script>

<style scoped lang="scss">
.slide-up-enter-active,
.slide-up-leave-active {
  transition: transform 1s ease;
}

.slide-up-enter,
.slide-up-leave-to {
  transform: translateY(100%);
}

.slide-up-enter-active {
  animation: slideUp 1s ease forwards;
}

@keyframes slideUp {
  from {
    transform: translateY(100%);
  }
  to {
    transform: translateY(0);
  }
}
</style>
