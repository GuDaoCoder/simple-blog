<template>
  <svg ::width="size" :height="size">
    <use :xlink:href="'#icon-' + props.name" :fill="color"></use>
  </svg>
</template>

<script setup lang="ts">
const props = defineProps({
  // 图标名称
  name: {
    type: String,
    required: true
  },
  // 颜色
  color: {
    type: String,
    default: '#616161'
  },
  // 尺寸
  size: { type: Number, default: 16 }
})
</script>
