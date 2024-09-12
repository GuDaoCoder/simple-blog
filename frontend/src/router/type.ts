import 'vue-router'
import { defineComponent } from 'vue'
import type { RouteMeta } from 'vue-router'

export type Component<T = any> =
  | ReturnType<typeof defineComponent>
  | (() => Promise<typeof import('*.vue')>)
  | (() => Promise<T>)

declare module 'vue-router' {
  export interface RouteMeta {
    title: string
    icon?: string
  }
}

export interface RouteRaw {
  path: string
  component: Component | string
  name: string
  redirect?: string
  children?: Array<RouteRaw>
  meta?: RouteMeta
}
