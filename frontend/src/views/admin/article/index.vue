<template>
  <content-card>
    <tiny-form label-width="80px" label-position="right" @submit="fetchTableData">
      <tiny-row flex>
        <tiny-col :span="4">
          <tiny-form-item label="文章Id">
            <tiny-input v-model="queryForm.articleId" placeholder="请输入文章Id" />
          </tiny-form-item>
        </tiny-col>
        <tiny-col :span="4">
          <tiny-form-item label="标题">
            <tiny-input v-model="queryForm.title" placeholder="请输入标题" />
          </tiny-form-item>
        </tiny-col>
      </tiny-row>
      <search-button-group>
        <tiny-button type="primary" native-type="submit">查询</tiny-button>
        <tiny-button type="info" @click="handleResetForm">重置</tiny-button>
      </search-button-group>
    </tiny-form>
  </content-card>

  <content-card class="mt-4">
    <tiny-grid
      :data="tableData"
      auto-resize
      :border="true"
      :stripe="true"
      highlight-current-row
      highlight-hover-row
    >
      <tiny-grid-column type="index" width="60" />
      <tiny-grid-column title="封面" width="120px">
        <template #default="data">
          <tiny-image
            class="cover-image"
            src="https://res.hc-cdn.com/tiny-vue-web-doc/3.18.9.20240902190525/static/images/mountain.png"
            fit="fit"
            lazy
          ></tiny-image>
        </template>
      </tiny-grid-column>
      <tiny-grid-column
        field="articleId"
        title="文章Id"
        show-overflow
        show-header-tip
        width="100px"
      />
      <tiny-grid-column field="title" title="标题" show-overflow show-header-tip width="180px" />
      <tiny-grid-column field="summary" title="摘要" show-overflow show-header-tip />
      <tiny-grid-column title="标签" show-overflow show-header-tip>
        <template #default="data">
          <!-- 宽度不够时自动换行 -->
          <div style="white-space: normal">
            <tiny-tag
              class="blog-tag"
              v-for="tag in data.row.tags"
              :key="tag.tagId"
              :value="tag.tagName"
              :color="tag.color"
              effect="dark"
            />
          </div>
        </template>
      </tiny-grid-column>
      <tiny-grid-column field="status" title="状态" show-overflow show-header-tip width="80px">
        <template #default="data">
          <tiny-tag
            effect="dark"
            :type="getStatusTagType(data.row)"
            :value="dictionary.articleStatus[data.row.status]"
        /></template>
      </tiny-grid-column>
      <tiny-grid-column field="top" title="是否置顶" show-overflow show-header-tip width="80px">
        <template #default="data">
          <tiny-switch v-model="data.row.top" show-text disabled>
            <template #open>
              <span>是</span>
            </template>
            <template #close>
              <span>否</span>
            </template>
          </tiny-switch>
        </template>
      </tiny-grid-column>
      <tiny-grid-column
        field="enableComment"
        title="是否开启评论"
        show-overflow
        show-header-tip
        width="100px"
      >
        <template #default="data">
          <tiny-switch v-model="data.row.enableComment" show-text disabled>
            <template #open>
              <span>是</span>
            </template>
            <template #close>
              <span>否</span>
            </template>
          </tiny-switch>
        </template>
      </tiny-grid-column>
      <tiny-grid-column
        field="createTime"
        title="创建时间"
        show-overflow
        show-header-tip
        width="160px"
      />
      <tiny-grid-column title="操作" fixed="right" width="150px">
        <template #default="data">
          <tiny-action-menu
            :options="getOperateOptions(data.row)"
            :max-show-num="3"
            @item-click="handleActionClick"
          />
        </template>
      </tiny-grid-column>
    </tiny-grid>

    <table-page
      :pagination="pagination"
      @current-page-change="handleChangePageNumber"
      @page-size-change="handleChangePageSize"
    />
  </content-card>
</template>

<script setup lang="ts">
import SearchButtonGroup from '@components/SearchButtonGroup/index.vue'
import TablePage from '@components/TablePage/index.vue'
import { onMounted, ref, reactive } from 'vue'
import { queryArticles, publishArticle, unpublishArticle } from '@api/article'
import { dictionary } from '@/utils/dictionary.ts'
import { notifySuccess, notifyWarning } from '@utils/notify'

/**
 * 默认操作栏操作项配置
 */
const defaultActionOptions = ref([
  {
    label: '设置封面'
  },
  {
    label: '预览'
  }
])

/**
 * 文章状态配置
 */
const articleStatusConfig = reactive({
  // 已发布
  PUBLISHED: {
    // 表格状态标签显示类型
    type: 'success',
    // 操作栏操作项配置
    actionOptions: [
      {
        label: '下架',
        action: (articleId) => handleUnpublishArticle(articleId)
      }
    ]
  },
  // 未发布
  UNPUBLISHED: {
    type: 'info',
    actionOptions: [
      {
        label: '发布',
        action: (articleId) => handlePublishArticle(articleId)
      }
    ]
  }
})

/**
 * 获取文章状态标签显示类型
 * @param row
 */
const getStatusTagType = (row: ApiArticle.QueryResponse) => {
  if (row.status) {
    return articleStatusConfig[row.status]?.type || 'info'
  } else {
    return 'info'
  }
}

/**
 * 根据每行数据内容获取对应的操作项
 * @param row
 */
const getOperateOptions = (row: ApiArticle.QueryResponse) => {
  let findConfig = articleStatusConfig[row.status]
  let actionOptions = findConfig
    ? defaultActionOptions.value.concat(findConfig.actionOptions)
    : defaultActionOptions.value
  return actionOptions.map((item) => ({
    ...item,
    row
  }))
}

const initQueryForm = (): Partial<ApiArticle.QueryForm> => {
  return {
    tagName: ''
  }
}

const queryForm = ref<Partial<ApiArticle.QueryForm>>(initQueryForm())

const pagination = ref<Component.Pagination>({
  pageNumber: 1,
  pageSize: 10,
  total: 0
})

const tableData = ref<ApiArticle.QueryResponse[]>([])

onMounted(() => {
  fetchTableData()
})

const fetchTableData = () => {
  let params = {
    pageNumber: pagination.value.pageNumber,
    pageSize: pagination.value.pageSize,
    ...queryForm.value
  }
  queryArticles(params).then((res) => {
    tableData.value = res.items
    pagination.value.total = res.total
  })
}

const handleResetForm = () => {
  queryForm.value = initQueryForm()
  fetchTableData()
}

const handleChangePageNumber = (pageNumber: number) => {
  pagination.value.pageNumber = pageNumber
  fetchTableData()
}

const handleChangePageSize = (pageSize: number) => {
  pagination.value.pageSize = pageSize
  fetchTableData()
}

/**
 * 操作项按钮点击时间
 * @param data
 */
const handleActionClick = (data) => {
  if (data.itemData.action) {
    data.itemData.action(data.itemData.row.articleId)
  } else {
    notifyWarning('操作项功能未配置')
  }
}

/**
 * 发布文章
 * @param articleId
 */
const handlePublishArticle = (articleId: number) => {
  publishArticle(articleId).then(() => {
    notifySuccess('发布成功')
    fetchTableData()
  })
}

/**
 * 下架文章
 * @param articleId
 */
const handleUnpublishArticle = (articleId: number) => {
  unpublishArticle(articleId).then(() => {
    notifySuccess('下架成功')
    fetchTableData()
  })
}
</script>

<style scoped lang="scss">
.cover-image {
  width: 100px;
  height: 100px;
}
.blog-tag {
  margin-left: 5px;
  margin-top: 5px;
}
.blog-tag:first-child {
  margin-left: 0px;
  margin-top: 0px;
}
</style>
