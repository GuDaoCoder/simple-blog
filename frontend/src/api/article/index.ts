import { get } from '../api'

/**
 * 查询文章
 * @param params
 * @returns
 */
export const queryArticles = (params: ApiArticle.QueryRequest) => {
  return get<ApiCommon.PageResponse<ApiArticle.QueryResponse>>('/admin/articles', params)
}
