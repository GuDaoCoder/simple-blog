import 'vue-router'

declare module 'vue-router' {
  interface RouteMeta {
    icon?: string
    title?: string
    hideInMenu?: boolean
    hideChildrenInMenu?: boolean
    order?: number
  }
}
