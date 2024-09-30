<template>
  <tiny-card title="这是logo类型" type="logo" size="mini" :src="userHead" auto-width>
    <p>这是一段长文本内容，这是一段长文本内容</p>
  </tiny-card>

  <tiny-card title="这是logo类型" type="logo" size="mini" :src="userHead" auto-width>
    <p>这是一段长文本内容，这是一段长文本内容</p>
  </tiny-card>

  <tiny-card title="这是logo类型" type="logo" size="mini" :src="userHead" auto-width>
    <p>这是一段长文本内容，这是一段长文本内容</p>
  </tiny-card>

  <tiny-card title="这是logo类型" type="logo" size="mini" :src="userHead" auto-width>
    <p>这是一段长文本内容，这是一段长文本内容</p>
  </tiny-card>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const userHead = ref(
  `https://res.hc-cdn.com/tiny-vue-web-doc/3.18.9.20240902190525/static/images/user-head.png`
)
</script>

<style scoped></style>
