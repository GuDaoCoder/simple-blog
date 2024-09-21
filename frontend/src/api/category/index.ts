import { get } from '../api'

export const treeCategories = (params: ApiCategory.QueryRequest) => {
  return get<ApiCategory.QueryResponse[]>('/admin/categories/tree', params)
}