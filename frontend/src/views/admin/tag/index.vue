<template>
  <content-card>
    <a-form :model="queryForm" @submit="handleFetchTableData">
      <a-row :gutter="16">
        <a-col :span="8">
          <a-form-item label="标签名称">
            <a-input v-model="queryForm.tagName" placeholder="请输入标签名称" allow-clear />
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
      row-key="tagId"
      stripe
    >
      <template #tagName="{ record }">
        <a-tag :color="record.color">{{ record.tagName }}</a-tag>
      </template>
    </a-table>

    <Pagination
      :pagination="pagination"
      @change="handleChangePageNumber"
      @page-size-change="handleChangePageSize"
    />
  </content-card>
</template>

<script setup lang="ts">
import SearchButtonGroup from '@components/SearchButtonGroup/index.vue'
import { queryTags } from '@api/tag'
import type { TableColumnData } from '@arco-design/web-vue'

const initQueryForm = (): Partial<ApiTag.QueryForm> => {
  return {
    tagName: ''
  }
}

const queryForm = ref<Partial<ApiTag.QueryForm>>(initQueryForm())

const pagination = ref<Pagination>({
  pageNumber: 1,
  pageSize: 10,
  total: 0
})

const tableColumns = ref<TableColumnData[]>([
  {
    title: '标签名称',
    dataIndex: 'tagName',
    slotName: 'tagName'
  },
  {
    title: '创建时间',
    dataIndex: 'createTime'
  }
])

const tableData = ref<ApiTag.QueryResponse[]>([])

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
  queryTags(params)
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
</script>

<style scoped lang="scss"></style>
