<template>
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
  <tiny-divider />
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
        <tiny-tag
          class="blog-tag"
          v-for="tag in data.row.tags"
          :key="tag.tagId"
          :value="tag.tagName"
          :color="tag.color"
          effect="dark"
        />
      </template>
    </tiny-grid-column>
    <tiny-grid-column field="status" title="状态" show-overflow show-header-tip width="60px">
      <template #default="data">{{ dictionary.articleStatus[data.row.status] }}</template>
    </tiny-grid-column>
    <tiny-grid-column field="source" title="文章来源" show-overflow show-header-tip width="80px">
      <template #default="data">{{ dictionary.articleSource[data.row.source] }}</template>
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
      <tiny-action-menu :options="operateOptions" max-show-num="3" />
    </tiny-grid-column>
  </tiny-grid>

  <table-page
    :pagination="pagination"
    @current-page-change="handleChangePageNumber"
    @page-size-change="handleChangePageSize"
  />
</template>

<script setup lang="ts">
import SearchButtonGroup from '@components/SearchButtonGroup/index.vue'
import TablePage from '@components/TablePage/index.vue'
import { onMounted, ref } from 'vue'
import { queryArticles } from '@api/article'
import { dictionary } from '@/utils/dictionary.ts'

const operateOptions = ref([
  {
    label: '设置封面'
  },
  {
    label: '发布'
  },
  {
    label: '下架'
  }
])

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
</script>

<style scoped lang="scss">
.cover-image {
  width: 100px;
  height: 100px;
}
.blog-tag {
  margin-right: 5px;
}
.blog-tag:last-child {
  margin-right: 0px;
}
</style>
