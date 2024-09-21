import axios, {
  type AxiosInstance,
  type AxiosResponse,
  type InternalAxiosRequestConfig
} from 'axios'
import { notifyError } from '@utils/notify'

const axiosInstance: AxiosInstance = axios.create({
  baseURL: '/api',
  timeout: 5000
})

// 请求拦截器
axiosInstance.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
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
          notifyError('服务器异常')
          break
        case 404:
          notifyError('资源不存在或已被删除')
          break
        case 403:
          notifyError('禁止访问')
          break
        case 401:
          notifyError('未登录或者登陆信息已失效')
          break
        case 400:
          notifyError(error.response.data.errorMsg || 'Error')
          break
        default:
          notifyError('未知异常')
          break
      }
    } else {
      if (error.message) {
        notifyError(error.message)
      }
    }
    return Promise.reject(error)
  }
)

export default axiosInstance
