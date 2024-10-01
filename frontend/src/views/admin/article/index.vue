<template>
  <content-card>
    <a-form :model="queryForm" @submit="handleFetchTableData">
      <a-row :gutter="16">
        <a-col :span="8">
          <a-form-item label="文章标题">
            <a-input v-model="queryForm.title" placeholder="请输入文章标题" allow-clear />
          </a-form-item>
        </a-col>
      </a-row>

      <search-button-group>
        <a-button html-type="submit" type="primary">查询</a-button>
        <a-button type="outline" @click="handleResetForm">重置</a-button>
      </search-button-group>
    </a-form>
  </content-card>

  <content-card class="mt-4">
    <a-table
      :columns="tableColumns"
      :data="tableData"
      :loading="tableLoading"
      :pagination="false"
      column-resizable
      row-key="articleId"
      stripe
    >
      <template #coverImageUrl="{ record }">
        <a-image
          v-if="record.coverImageUrl"
          :src="record.coverImageUrl"
          height="100"
          show-loader
          width="160"
        />
      </template>
      <template #tags="{ record }">
        <div class="flex flex-wrap gap-[5px]">
          <a-tag
            v-for="item in record.tags"
            :key="item.tagName"
            :color="item.color"
            style="margin: 4px"
          >
            {{ item.tagName }}
          </a-tag>
        </div>
      </template>
      <template #status="{ record }">
        {{ record.status }}
      </template>
      <template #top="{ record }">
        {{ record.top }}
      </template>
      <template #enableComment="{ record }">
        {{ record.enableComment }}
      </template>
      <template #operations="{ record }">
        <a-button
          type="text"
          size="mini"
          v-for="action in getActions(record)"
          :key="action.label"
          @click="action.onClick(record)"
        >
          {{ action.label }}
        </a-button>
      </template>
    </a-table>

    <Pagination
      :pagination="pagination"
      @change="handleChangePageNumber"
      @page-size-change="handleChangePageSize"
    />
  </content-card>

  <upload-image
    :visible="uploadImageVisible"
    title="文章封面上传"
    module="COVER_IMAGE"
    @upload-success="handleUploadCoverImageSuccess"
    @close="handleUploadImageClose"
  />
</template>

<script setup lang="ts">
import SearchButtonGroup from '@components/SearchButtonGroup/index.vue'
import {
  queryArticles,
  publishArticle,
  unpublishArticle,
  updateArticleCoverImage
} from '@api/article'
import { Notification, type TableColumnData } from '@arco-design/web-vue'

const tableColumns = ref<TableColumnData[]>([
  {
    title: '封面',
    dataIndex: 'coverImageUrl',
    slotName: 'coverImageUrl',
    width: 180
  },
  {
    title: '文章标题',
    dataIndex: 'title',
    ellipsis: true,
    tooltip: { position: 'left' },
    width: 200
  },
  {
    title: '摘要',
    dataIndex: 'summary',
    ellipsis: true,
    width: 160,
    tooltip: { position: 'left' }
  },
  {
    title: '状态',
    dataIndex: 'status',
    slotName: 'status',
    width: 120
  },
  {
    title: '标签',
    dataIndex: 'tags',
    slotName: 'tags',
    width: 200
  },

  {
    title: '是否置顶',
    dataIndex: 'top',
    slotName: 'top',
    width: 100
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    width: 180
  },
  {
    title: '操作',
    dataIndex: 'operations',
    slotName: 'operations',
    fixed: 'right',
    width: 220
  }
])

interface Action {
  label: string
  show: (record: ApiArticle.QueryResponse) => boolean
  onClick: (record: ApiArticle.QueryResponse) => void
}

const actions = ref<Action[]>([
  {
    label: '设置封面',
    show: () => true,
    onClick: (record) => {
      handleOpenUploadCoverImageDialog(record)
    }
  },
  {
    label: '发布',
    show: (record) => record.status === 'UNPUBLISHED',
    onClick: (record) => {
      handlePublishArticle(record)
    }
  },
  {
    label: '下架',
    show: (record) => record.status === 'PUBLISHED',
    onClick: (record) => {
      handleUnPublishArticle(record)
    }
  }
])

const getActions = (record: ApiArticle.QueryResponse) => {
  return actions.value.filter((o) => o.show(record))
}

const initQueryForm = (): Partial<ApiArticle.QueryForm> => {
  return {
    articleId: undefined,
    title: ''
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
  handleFetchTableData()
})

const tableLoading = ref(false)

const handleFetchTableData = () => {
  let params = {
    pageNumber: pagination.value.pageNumber,
    pageSize: pagination.value.pageSize,
    ...queryForm.value
  }
  tableLoading.value = true
  queryArticles(params)
    .then((res) => {
      tableData.value = res.items
      pagination.value.total = res.total
    })
    .finally(() => (tableLoading.value = false))
}

const handleResetForm = () => {
  queryForm.value = initQueryForm()
  handleFetchTableData()
}

const handleChangePageNumber = (pageNumber: number) => {
  pagination.value.pageNumber = pageNumber
  handleFetchTableData()
}

const handleChangePageSize = (pageSize: number) => {
  pagination.value.pageSize = pageSize
  handleFetchTableData()
}

const uploadImageVisible = ref(false)
const selectedArticleId = ref<number>(0)

/**
 * 打开上传封面弹窗
 * @param articleId
 */
const handleOpenUploadCoverImageDialog = (record: ApiArticle.QueryResponse) => {
  selectedArticleId.value = record.articleId
  uploadImageVisible.value = true
}

/**
 * 上传封面成功
 * @param data
 */
const handleUploadCoverImageSuccess = (data: ApiCommon.Result<ApiAttachment.UploadResponse>) => {
  updateArticleCoverImage(selectedArticleId.value, data.data.url)
    .then(() => {
      Notification.success('封面更新成功')
      handleFetchTableData()
    })
    .finally(() => {
      selectedArticleId.value = 0
      uploadImageVisible.value = false
    })
}

/**
 * 关闭上传封面弹窗
 */
const handleUploadImageClose = () => {
  uploadImageVisible.value = false
}

/**
 * 发布文章
 * @param record
 */
const handlePublishArticle = (record: ApiArticle.QueryResponse) => {
  publishArticle(record.articleId).then(() => {
    Notification.success('发布成功')
    handleFetchTableData()
  })
}

/**
 * 下架文章
 * @param articleId
 */
const handleUnPublishArticle = (record: ApiArticle.QueryResponse) => {
  unpublishArticle(record.articleId).then(() => {
    Notification.success('下架成功')
    handleFetchTableData()
  })
}
</script>

<style scoped lang="scss">
:deep(.arco-btn-size-mini) {
  padding: 0 2px;
}
</style>
