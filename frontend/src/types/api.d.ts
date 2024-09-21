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

/**
 * 分类
 */
declare namespace ApiCategory {
  interface QueryForm {
    categoryName: string
  }

  type QueryRequest = ApiCommon.BaseRequest & Partial<QueryForm>

  interface QueryResponse {
    /**
     * 分类Id
     */
    categoryId: number
    /**
     * 分类名称
     */
    categoryName: string
    /**
     * 父级分类Id
     */
    parentCategoryId: number
    /**
     * 分类路径Id
     */
    fullId: string
    /**
     * 排序
     */
    orderNo: number
    /**
     * 层级
     */
    level: number
    /**
     * 文章数量
     */
    articleCount: number
    /**
     * 创建时间
     */
    createTime: string
    /**
     * 更新时间
     */
    updateTime: string
    /**
     * 子分类
     */
    children: QueryResponse[]
  }
}
