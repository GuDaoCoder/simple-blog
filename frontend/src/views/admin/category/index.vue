<template>
  <content-card>
    <tiny-form label-width="80px" label-position="right" @submit="fetchTableData">
      <tiny-row flex>
        <tiny-col :span="4">
          <tiny-form-item label="分类名称">
            <tiny-input v-model="queryForm.categoryName" placeholder="请输入分类名称"></tiny-input>
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
      :tree-config="{ children: 'children' }"
    >
      <tiny-grid-column type="index" width="60" />
      <tiny-grid-column field="categoryName" title="分类名称" tree-node />
      <tiny-grid-column field="articleCount" title="文章数量" />
      <tiny-grid-column field="createTime" title="创建时间" />
    </tiny-grid>
  </content-card>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { treeCategories } from '@api/category/index'

const initQueryForm = (): Partial<ApiCategory.QueryForm> => {
  return {
    categoryName: ''
  }
}

const queryForm = ref<Partial<ApiCategory.QueryForm>>(initQueryForm())

const tableData = ref<ApiCategory.QueryResponse[]>([])

onMounted(() => {
  fetchTableData()
})

const fetchTableData = () => {
  let params = {
    ...queryForm.value
  }
  treeCategories(params).then((res) => {
    tableData.value = res
  })
}

const handleResetForm = () => {
  queryForm.value = initQueryForm()
  fetchTableData()
}
</script>

<style scoped lang="scss"></style>
