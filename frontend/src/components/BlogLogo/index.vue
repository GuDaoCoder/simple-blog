<template>
  <img :src="logo" alt="" class="logo" @click="handleClickLogo" :style="`height:${height}px`" />
</template>

<script setup lang="ts">
import logo from '@/assets/image/logo.png'

const props = defineProps({
  link: {
    type: String,
    default: ''
  },
  height: {
    type: Number,
    default: 40
  }
})

const router = useRouter()
const handleClickLogo = () => {
  if (props.link) {
    router.push(props.link)
  }
}
</script>

<style scoped lang="scss">
.logo {
  cursor: pointer;
}
</style>
