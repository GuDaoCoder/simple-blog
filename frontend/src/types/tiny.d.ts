declare namespace TinyVue {
  interface ITreeNodeData {
    // node-key='id' 设置节点的唯一标识
    id: number | string
    // 节点显示文本
    label: string
    // 子节点
    children?: ITreeNodeData[]
    // 链接
    path?: string
    // show-number 时展示的字段
    number?: number | string
    // 自定义每个节点的图标
    customIcon: Component
  }
}
