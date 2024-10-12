import { createApp } from 'vue'
import '@/style/reset.scss'
import '@/style/global.scss'
import App from '@/App.vue'
import router from '@/router'
import store from '@/store'
import 'virtual:windi.css'

// svg图标
import 'virtual:svg-icons-register'

// markdown预览组件
import VMdPreview from '@kangc/v-md-editor/lib/preview'
import '@kangc/v-md-editor/lib/style/preview.css'
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js'
import '@kangc/v-md-editor/lib/theme/style/vuepress.css'
import createCopyCodePlugin from '@kangc/v-md-editor/lib/plugins/copy-code/index'
import '@kangc/v-md-editor/lib/plugins/copy-code/copy-code.css'
import createLineNumbertPlugin from '@kangc/v-md-editor/lib/plugins/line-number/index'
import Prism from 'prismjs'

VMdPreview
  // 主题
  .use(vuepressTheme, { Prism })
  // 复制按钮
  .use(createCopyCodePlugin())
  // 行号
  .use(createLineNumbertPlugin())

const app = createApp(App)
app.use(store).use(router).use(VMdPreview).mount('#app')
