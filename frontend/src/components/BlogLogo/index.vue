<template>
  <img :src="logo" alt="" class="logo" @click="handleClickLogo" />
</template>

<script setup lang="ts">
import logo from '@/assets/image/logo.png'

const props = defineProps({
  link: {
    type: String,
    default: ''
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
  height: 40px;
  cursor: pointer;
}
</style>
