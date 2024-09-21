<template>
  <tiny-pager
    align="right"
    mode="number"
    :current-page="pagination.pageNumber"
    :page-size="pagination.pageSize"
    :total="pagination.total"
    :page-sizes="[5, 10, 20, 50]"
    @current-change="handleChangePage"
    @size-change="handleChangePageSize"
  ></tiny-pager>
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

defineProps({
  pagination: {
    type: Object as PropType<Component.Pagination>,
    default() {
      return {
        pageNumber: 1,
        pageSize: 10,
        total: 0
      }
    }
  }
})

const emit = defineEmits(['currentPageChange', 'pageSizeChange'])

const handleChangePage = (pageNumber: number) => {
  emit('currentPageChange', pageNumber)
}

const handleChangePageSize = (pageSize: number) => {
  emit('pageSizeChange', pageSize)
}
</script>

<style scoped></style>
