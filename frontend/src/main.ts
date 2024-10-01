import { createApp } from 'vue'
import '@style/reset.scss'
import App from '@/App.vue'
import router from '@/router'
import store from '@/store'
import 'virtual:windi.css'

// svg图标
import 'virtual:svg-icons-register'

const app = createApp(App)
app.use(store).use(router).mount('#app')
