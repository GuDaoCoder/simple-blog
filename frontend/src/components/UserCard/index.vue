<template>
  <a-card :bordered="false" hoverable>
    <div class="w-full text-center">
      <a-avatar
        :size="84"
        class="enlarge"
        imageUrl="http://cdn.road4code.com/blog/WechatIMG21.jpg"
      />
    </div>

    <div class="w-full py-2 text-center text-[24px] enlarge">孤岛</div>
  </a-card>
</template>

<script setup lang="ts"></script>

<style scoped lang="scss">
.enlarge {
  transition: 0.5s;

  &:hover {
    transform: scale(1.2);
  }
}
</style>
