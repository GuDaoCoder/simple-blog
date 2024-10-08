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
 * 设置文章封面
 * @param articleId
 * @param coverImageUrl
 * @returns
 */
export const updateArticleCoverImage = (articleId: number, coverImageUrl: string) => {
  return patch(`/admin/articles/${articleId}/updateCoverImage`, { coverImageUrl })
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
export const unPublishArticle = (articleId: number) => {
  return patch(`/admin/articles/${articleId}/unPublish`)
}

/**
 * 获取文章列表
 * @param params
 */
export const portalQueryArticles = (params: ApiArticle.PortalQueryRequest) => {
  return get<ApiCommon.PageResponse<ApiArticle.QueryResponse>>('/portal/articles', params)
}

/**
 * 查询文章详情
 * @param articleId
 */
export const portalGetArticle = (articleId: number) => {
  return get<ApiArticle.QueryResponse>(`/portal/articles/${articleId}`)
}

/**
 * 查询文章内容
 * @param articleId
 */
export const portalArticleContent = (articleId: number) => {
  return get<string>(`/portal/articles/${articleId}/content`)
}
