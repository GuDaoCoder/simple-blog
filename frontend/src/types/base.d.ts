interface Dictionary {
  [key: string]: string | { [key: string]: string }
}

interface Menu {
  path: string
  title: string
  icon?: string
  children?: Menu[]
}

declare namespace Component {
  interface Pagination {
    pageNumber: number
    pageSize: number
    total: number
  }
}
