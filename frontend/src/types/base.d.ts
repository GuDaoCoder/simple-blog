interface Dictionary {
  [key: string]: string | { [key: string]: string }
}

interface Menu {
  name?: string | symbol
  path: string
  title: string
  icon?: string
  children?: Menu[]
}

interface Pagination {
  pageNumber: number
  pageSize: number
  total: number
}

declare namespace Component {
  interface Pagination {
    pageNumber: number
    pageSize: number
    total: number
  }
}
