import { get } from '../api'

/**
 * 查询标签
 * @param params
 * @returns
 */
export const queryTags = (params: ApiTag.QueryRequest) => {
  return get<ApiCommon.PageResponse<ApiTag.QueryResponse>>('/admin/tags', params)
}
