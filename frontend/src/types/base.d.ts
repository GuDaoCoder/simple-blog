interface Dictionary {
  [key: string]: string | { [key: string]: string }
}

declare namespace Component {
  interface Pagination {
    pageNumber: number
    pageSize: number
    total: number
  }
}
