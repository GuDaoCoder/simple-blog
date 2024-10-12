<template>
  <a-space direction="vertical" class="w-full">
    <user-card />
    <transition name="slide-up">
      <a-affix :offsetTop="70">
        <div v-if="showArticleRelationCard">
          <anchor-card />
        </div>
      </a-affix>
    </transition>
    <transition name="slide-up">
      <div v-if="showHomeCard">
        <category-card />
        <tag-card />
      </div>
    </transition>
  </a-space>
</template>

<script setup lang="ts">
const route = useRoute()
const showArticleRelationCard = computed(() => {
  return route.name === 'article'
})

const showHomeCard = computed(() => {
  return route.name === 'home'
})
</script>

<style scoped lang="scss">
.slide-up-enter-active,
.slide-up-leave-active {
  transition: transform 0.5s ease;
}

.slide-up-enter,
.slide-up-leave-to {
  transform: translateY(100%);
}

.slide-up-enter-active {
  animation: slideUp 0.5s ease forwards;
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
