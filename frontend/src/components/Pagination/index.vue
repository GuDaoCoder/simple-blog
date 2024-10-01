<template>
  <a-pagination
    class="mt-4 justify-end"
    :current="pagination.pageNumber"
    :page-size="pagination.pageSize"
    :total="pagination.total"
    show-page-size
    show-total
    @change="handleChangePage"
    @page-size-change="handleChangePageSize"
  />
</template>

<script setup lang="ts">
import type { PropType } from 'vue'

defineProps({
  pagination: {
    type: Object as PropType<Pagination>,
    default() {
      return {
        pageNumber: 1,
        pageSize: 10,
        total: 0
      }
    }
  }
})

const emit = defineEmits(['change', 'pageSizeChange'])

const handleChangePage = (pageNumber: number) => {
  emit('change', pageNumber)
}

const handleChangePageSize = (pageSize: number) => {
  emit('pageSizeChange', pageSize)
}
</script>

<style scoped></style>
