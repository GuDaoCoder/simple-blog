<template>
  <content-card>
    <tiny-form label-width="80px" label-position="right" @submit="fetchTableData">
      <tiny-row flex>
        <tiny-col :span="4">
          <tiny-form-item label="标签名称">
            <tiny-input v-model="queryForm.tagName" placeholder="请输入标签名称"></tiny-input>
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
      <tiny-grid-column field="tagName" title="标签名称">
        <template #default="data">
          <tiny-tag :value="data.row.tagName" :color="data.row.color" effect="dark" />
        </template>
      </tiny-grid-column>
      <tiny-grid-column field="createTime" title="创建时间" />
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
import { onMounted, ref } from 'vue'
import { queryTags } from '@api/tag'

const initQueryForm = (): Partial<ApiTag.QueryForm> => {
  return {
    tagName: ''
  }
}

const queryForm = ref<Partial<ApiTag.QueryForm>>(initQueryForm())

const pagination = ref<Component.Pagination>({
  pageNumber: 1,
  pageSize: 10,
  total: 0
})

const tableData = ref<ApiTag.QueryResponse[]>([])

onMounted(() => {
  fetchTableData()
})

const fetchTableData = () => {
  let params = {
    pageNumber: pagination.value.pageNumber,
    pageSize: pagination.value.pageSize,
    ...queryForm.value
  }
  queryTags(params).then((res) => {
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
:deep(.tiny-grid-cell) {
  line-height: 42px;
}
</style>
