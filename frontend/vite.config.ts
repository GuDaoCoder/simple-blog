import { fileURLToPath, URL } from 'node:url'

import { ConfigEnv, defineConfig, loadEnv, UserConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import autoImportPlugin from '@opentiny/unplugin-tiny-vue'
import WindiCSS from 'vite-plugin-windicss'

// https://vitejs.dev/config/
export default defineConfig(({ mode }: ConfigEnv): UserConfig => {
  const env = loadEnv(mode, process.cwd())
  return {
    plugins: [vue(), WindiCSS(), vueJsx(), autoImportPlugin('vite')],
    resolve: {
      alias: {
        '@': fileURLToPath(new URL('./src', import.meta.url)), // 源码根目录
        '@style': fileURLToPath(new URL('./src/style', import.meta.url)), // 样式
        '@img': fileURLToPath(new URL('./src/assets/img', import.meta.url)), // 图片
        '@plugins': fileURLToPath(new URL('./src/plugins', import.meta.url)), // 本地插件
        '@components': fileURLToPath(new URL('./src/components', import.meta.url)), // 公共组件
        '@layout': fileURLToPath(new URL('./src/layout', import.meta.url)), // 布局
        '@config': fileURLToPath(new URL('./src/config', import.meta.url)), // 配置
        '@store': fileURLToPath(new URL('./src/store', import.meta.url)), // 状态管理
        '@utils': fileURLToPath(new URL('./src/utils', import.meta.url)), // 工具类
        '@views': fileURLToPath(new URL('./src/views', import.meta.url)), // 路由组件
        '@types': fileURLToPath(new URL('./src/types', import.meta.url)), // 类型
        '@api': fileURLToPath(new URL('./src/api', import.meta.url)) // api
      }
    },
    server: {
      proxy: {
        '/api': {
          target: env.VITE_APP_BASE_API,
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, '')
        }
      }
    }
  }
})
