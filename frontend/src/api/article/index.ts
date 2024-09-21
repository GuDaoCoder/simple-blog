import { get, patch } from '../api'

/**
 * 查询文章
 * @param params
 * @returns
 */
export const queryArticles = (params: ApiArticle.QueryRequest) => {
  return get<ApiCommon.PageResponse<ApiArticle.QueryResponse>>('/admin/articles', params)
}

/**
 * 发布文章
 * @param articleId
 * @returns
 */
export const publishArticle = (articleId: number) => {
  return patch(`/admin/articles/${articleId}/publish`)
}

/**
 * 下架文章
 * @param articleId
 * @returns
 */
export const unpublishArticle = (articleId: number) => {
  return patch(`/admin/articles/${articleId}/unpublish`)
}
