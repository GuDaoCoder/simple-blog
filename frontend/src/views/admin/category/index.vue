<template>
  <content-card>
    <a-form :model="queryForm" @submit="handleFetchTableData">
      <a-row :gutter="16">
        <a-col :span="8">
          <a-form-item label="分类名称">
            <a-input v-model="queryForm.categoryName" placeholder="分类名称" allow-clear />
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
      hide-expand-button-on-empty
      row-key="categoryId"
      stripe
    >
      <template #articleCount="{ record }">
        {{ handleTotalCount(record) }}
      </template>
    </a-table>
  </content-card>
</template>

<script setup lang="ts">
import { treeCategories } from '@api/category'
import SearchButtonGroup from '@components/SearchButtonGroup/index.vue'
import type { TableColumnData } from '@arco-design/web-vue'

const initQueryForm = (): Partial<ApiCategory.QueryForm> => {
  return {
    categoryName: ''
  }
}

const queryForm = ref<Partial<ApiCategory.QueryForm>>(initQueryForm())

const tableData = ref<ApiCategory.QueryResponse[]>([])

const tableColumns = ref<TableColumnData[]>([
  {
    title: '分类名称',
    dataIndex: 'categoryName'
  },
  {
    title: '文章数量',
    dataIndex: 'articleCount',
    slotName: 'articleCount'
  },
  {
    title: '创建时间',
    dataIndex: 'createTime'
  }
])

onMounted(() => {
  handleFetchTableData()
})

const tableLoading = ref(false)
const handleFetchTableData = () => {
  let params = {
    ...queryForm.value
  }
  tableLoading.value = true
  treeCategories(params)
    .then((res) => {
      tableData.value = res
    })
    .finally(() => (tableLoading.value = false))
}

const handleResetForm = () => {
  queryForm.value = initQueryForm()
  handleFetchTableData()
}

const handleTotalCount = (record: ApiCategory.QueryResponse): number => {
  let count = record.articleCount || 0
  if (record.children && record.children.length > 0) {
    record.children.forEach((item) => {
      count += handleTotalCount(item)
    })
  }
  return count
}
</script>

<style scoped lang="scss"></style>
