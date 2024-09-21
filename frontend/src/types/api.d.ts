declare namespace ApiCommon {
  interface BaseRequest {
    /**
     * 请求Id
     */
    //requestId: string
  }

  interface PageRequest extends BaseRequest {
    /**
     * 页码
     */
    pageNumber: number
    /**
     * 每页数量
     */
    pageSize: number
  }

  interface PageResponse<T> {
    /**
     * 页码
     */
    pageNumber: number
    /**
     * 每页数量
     */
    pageSize: number
    /**
     * 总数
     */
    total: number

    /**
     * 分页数据
     */
    items: T[]
  }

  interface Result<T> {
    /**
     * 请求时间戳
     */
    timestamp: number

    /**
     * 错误信息
     */
    errorMsg: string

    /**
     * 返回数据
     */
    data: T
  }
}

/**
 * 标签
 */
declare namespace ApiTag {
  interface QueryForm {
    /**
     *标签名称
     */
    tagName: string
  }

  type QueryRequest = ApiCommon.PageRequest & Partial<QueryForm>

  interface QueryResponse {
    /**
     * 标签Id
     */
    tagId: number
    /**
     * 标签名称
     */
    tagName: string
    /**
     * 颜色
     */
    color: string
    /**
     * 创建时间
     */
    createTime: string
    /**
     * 更新时间
     */
    updateTime: string
  }
}
