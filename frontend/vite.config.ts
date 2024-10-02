import { fileURLToPath, URL } from 'node:url'
import path from 'path'
import { ConfigEnv, defineConfig, loadEnv, UserConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import WindiCSS from 'vite-plugin-windicss'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { vitePluginForArco } from '@arco-plugins/vite-vue'
import { createSvgIconsPlugin } from 'vite-plugin-svg-icons'
import { prismjsPlugin } from 'vite-plugin-prismjs'

// https://vitejs.dev/config/
export default defineConfig(({ mode }: ConfigEnv): UserConfig => {
  const env = loadEnv(mode, process.cwd())
  return {
    plugins: [
      vue(),
      WindiCSS(),
      vueJsx(),
      // 未vue、vue-router、pinia按需自动引入
      AutoImport({
        dts: 'src/types/auto-imports.d.ts',
        imports: ['vue', 'vue-router', 'pinia'],
        eslintrc: {
          enabled: false,
          filepath: './.eslintrc-auto-import.json',
          globalsPropValue: true
        }
      }),
      // 自动引入自定义组件
      Components({
        dirs: ['src/components']
      }),
      // 按需引入arco
      vitePluginForArco({
        style: 'css'
      }),
      // svg图标
      createSvgIconsPlugin({
        iconDirs: [path.resolve(process.cwd(), 'src/assets/icons')],
        symbolId: 'icon-[dir]-[name]'
      }),
      // markdown语法高亮
      prismjsPlugin({
        languages: ['json', 'xml', 'java', 'sql', 'javascript', 'yml', 'yaml']
      })
    ],
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
