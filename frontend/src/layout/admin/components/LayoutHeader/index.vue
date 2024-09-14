<template>
  <div class="navbar">
    <div class="left-sider">
      <IconApplication />
      <tiny-breadcrumb separator="/" :options="breadcrumboptions" />
    </div>
    <ul class="right-sider">
      <li>
        <tiny-user-head type="icon" round min></tiny-user-head>
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { iconApplication } from '@opentiny/vue-icon'
import { computed, ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
console.log(route.matched)

const IconApplication = iconApplication()

const breadcrumboptions = computed(() =>
  route.matched.filter((o) => o.meta.title).map((o) => ({ label: o.meta.title }))
)
</script>

<style scoped lang="scss">
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
  padding: 0 20px;
  border-bottom: 1px solid #d8dae2;
}

.left-sider {
  display: flex;
  gap: 10px;
}

.right-sider {
  display: flex;
  li {
    display: flex;
    align-items: center;
  }
}
</style>
