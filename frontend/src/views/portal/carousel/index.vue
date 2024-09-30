<template>
  <!-- <tiny-carousel height="250px">
    <tiny-carousel-item v-for="item in 4" :key="item"> -->
  <tiny-card title="这是卡片标题" type="image" :src="url">
    <p>
      这是一段长文本内容，这是一段长文本内容，这是一段长文本内容，这是一段长文本内容，
      这是一段长文本内容，这是一段长文本内容，这是一段长文本内容，这是一段长文本内容，
    </p>
  </tiny-card>
  <!-- </tiny-carousel-item>
  </tiny-carousel> -->
</template>

<script setup lang="ts">
import { ref } from 'vue'

const url = ref(
  'https://res.hc-cdn.com/tiny-vue-web-doc/3.18.9.20240902190525/static/images/dsj.png'
)
</script>

<style scoped lang="scss"></style>
