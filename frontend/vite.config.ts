import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import autoImportPlugin from '@opentiny/unplugin-tiny-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue(), vueJsx(), autoImportPlugin('vite')],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)), // 源码根目录
      '@style': fileURLToPath(new URL('./src/style', import.meta.url)), // 样式
      '@img': fileURLToPath(new URL('./src/assets/img', import.meta.url)), // 图片
      '@plugins': fileURLToPath(new URL('./src/plugins', import.meta.url)), // 本地插件
      '@components': fileURLToPath(new URL('./src/components', import.meta.url)), // 公共组件
      '@views': fileURLToPath(new URL('./src/views', import.meta.url)) // 路由组件
    }
  }
})
