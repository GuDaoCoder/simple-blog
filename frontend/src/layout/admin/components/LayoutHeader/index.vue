<template>
  <div class="navbar">
    <div></div>
    <ul class="right-sider">
      <li>
        <tiny-user-head type="icon" round min></tiny-user-head>
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped lang="scss">
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
  padding: 0 20px;
  box-shadow: 0 4px 6px #0003;
}

.right-sider {
  display: flex;
  li {
    display: flex;
    align-items: center;
  }
}
</style>
