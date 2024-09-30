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
export const unpublishArticle = (articleId: number) => {
  return patch(`/admin/articles/${articleId}/unpublish`)
}
