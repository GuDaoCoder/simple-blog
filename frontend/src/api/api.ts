import axiosInstance from './request'

export async function get<T>(url: string, params?: any): Promise<T> {
  const response = await axiosInstance.get<ApiCommon.Result<T>>(url, { params })
  return response.data.data
}

export async function post<T>(url: string, data?: any): Promise<T> {
  const response = await axiosInstance.post<ApiCommon.Result<T>>(url, data)
  return response.data.data
}

export async function put<T>(url: string, data?: any): Promise<T> {
  const response = await axiosInstance.put<ApiCommon.Result<T>>(url, data)
  return response.data.data
}

export async function del<T>(url: string, params?: any): Promise<T> {
  const response = await axiosInstance.delete<ApiCommon.Result<T>>(url, { params })
  return response.data.data
}
