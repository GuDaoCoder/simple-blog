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

/**
 * 文章
 */
declare namespace ApiArticle {
  interface QueryForm {
    /**
     * 文章Id
     */
    articleId: number
    /**
     * 标题
     */
    title: string
    /**
     * 状态
     */
    status: 'PUBLISHED' | 'UNPUBLISHED'
    /**
     * 文章来源
     */
    source: 'GIT_SYNC'
    /**
     * 所属分类Id
     */
    categoryId: number
  }

  type QueryRequest = ApiCommon.PageRequest & Partial<QueryForm>

  interface QueryResponse {
    /**
     * 文章Id
     */
    articleId: number
    /**
     * 标题
     */
    title: string
    /**
     * 摘要
     */
    summary: string
    /**
     * 封面图片链接
     */
    coverImageUrl: string
    /**
     * 状态
     */
    status: 'PUBLISHED' | 'UNPUBLISHED'
    /**
     * 文章来源
     */
    source: 'GIT_SYNC'
    /**
     * 所属分类Id
     */
    categoryId: number
    /**
     * 是否置顶
     */
    top: boolean
    /**
     * 是否开启评论
     */
    enableComment: boolean
    /**
     * 发布时间
     */
    publishTime: string
    /**
     * 下架时间
     */
    unpublishTime: string
    /**
     * 文件hash值
     */
    fileHash: string
    /**
     * 创建时间
     */
    createTime: string
    /**
     * 更新时间
     */
    updateTime: string
    /**
     * 标签信息
     */
    tags: ApiTag.QueryResponse[]
  }
}

/**
 * 附件
 */
declare namespace ApiAttachment {
  interface UploadResponse {
    /**
     * 附件id
     */
    attachmentId: number
    /**
     * 文件原名称
     */
    originalName: string
    /**
     * 附件存储名称
     */
    storageName: string
    /**
     * 扩展名
     */
    extension: string
    /**
     * 附件路径
     */
    path: string
    /**
     * 文件大小
     */
    size: number
    /**
     * 存储策略
     */
    storagePolicy: 'LOCAL'
    /**
     * 文件hash值
     */
    fileHash: string
    /**
     * 所属模块
     */
    module: 'IMAGE_BED' | 'COVER_IMAGE'
    /**
     * 创建时间
     */
    createTime: string
    /**
     * 更新时间
     */
    updateTime: string
    /**
     * 访问地址
     */
    url: string
  }
}
