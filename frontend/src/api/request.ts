import axios, {
  type AxiosInstance,
  type AxiosResponse,
  type InternalAxiosRequestConfig
} from 'axios'
import { Notification } from '@arco-design/web-vue'
import { getToken } from '@/utils/auth'
import router from '@/router'

const axiosInstance: AxiosInstance = axios.create({
  baseURL: '/api',
  timeout: 5000
})

// 请求拦截器
axiosInstance.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = getToken()
    if (token) {
      config.headers['Authorization'] = token
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// 响应拦截器
axiosInstance.interceptors.response.use(
  (response: AxiosResponse) => {
    return response
  },
  (error: any) => {
    if (error.response) {
      switch (error.response.status) {
        case 500:
          Notification.error('服务器异常')
          break
        case 404:
          Notification.error('资源不存在或已被删除')
          break
        case 403:
          Notification.error('禁止访问')
          break
        case 401:
          Notification.error('未登录或者登陆信息已失效')
          router.push({ name: 'admin-login' })
          break
        case 400:
          Notification.error(error.response.data.errorMsg || 'Error')
          break
        default:
          Notification.error('未知异常')
          break
      }
    } else {
      if (error.message) {
        Notification.error(error.message)
      }
    }
    return Promise.reject(error)
  }
)

export default axiosInstance
