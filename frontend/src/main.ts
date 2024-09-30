import { createApp } from 'vue'
import '@style/reset.scss'
import App from '@/App.vue'
import router from '@/router'
import store from '@/store'
import 'virtual:windi.css'

// 主题
import TinyThemeTool from '@opentiny/vue-theme/theme-tool'
import { tinySmbTheme } from '@opentiny/vue-theme/theme'

const app = createApp(App)
app.config.globalProperties.theme = new TinyThemeTool(tinySmbTheme, 'tinyStyleSheetId')
app.use(store).use(router).mount('#app')
