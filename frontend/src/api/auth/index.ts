import { post } from '../api'

/**
 * 登陆
 * @param form
 */
export const adminLogin = (data: ApiAuth.LoginRequest) => {
  return post<ApiAuth.LoginResponse>('/admin/login', data)
}
