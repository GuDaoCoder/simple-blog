<template>
  <div class="w-full h-full bg-white">
    <p
      class="text-[24px] text-[#333] font-bold text-center pt-[40px] break-words shadow-[0_1px_2px_rgba(0,0,0,0.25)]"
    >
      {{ article.title }}
    </p>
    <a-space class="text-[16px] px-10 py-5 text-[#909399]">
      <span>{{ formatDate(article.publishTime, 'YYYY/MM/DD') }}</span>
    </a-space>

    <a-divider />

    <v-md-preview
      ref="mdPreview"
      :text="articleContent"
      @copy-code-success="handleCopyCodeSuccess"
    ></v-md-preview>

    <a-divider />

    <div class="flex justify-between items-center px-10 py-4">
      <a-space>
        <span class="font-bold">标签：</span>
        <a-tag v-for="tag in article.tags" :key="tag.tagId" :color="tag.color">
          {{ tag.tagName }}
        </a-tag>
      </a-space>

      <a-space>
        <a-button type="primary">
          <template #icon>
            <icon-heart-fill />
          </template>
        </a-button>
        <a-button type="primary">
          <template #icon>
            <icon-thumb-up-fill />
          </template>
        </a-button>
      </a-space>
    </div>
  </div>
</template>

<script setup lang="ts">
import { portalGetArticle, portalArticleContent } from '@/api/article'
import { formatDate } from '@/utils/date'
import { Notification } from '@arco-design/web-vue'
import useAnchorStore from '@/store/modules/anchor'
import { nextTick } from 'vue'

const route = useRoute()

const article = ref<ApiArticle.QueryResponse>(<ApiArticle.QueryResponse>{})
const articleContent = ref('')

const mdPreview = ref()

onMounted(() => {
  const articleId = route.params.articleId as unknown as number
  getPortalArticle(articleId)
  getPortalArticleContent(articleId)
})

onUnmounted(() => {
  anchorStore.clear()
})

const getPortalArticle = (articleId: number) => {
  portalGetArticle(articleId).then((res) => {
    article.value = res
  })
}

const anchorStore = useAnchorStore()

/**
 * 获取文章详情
 * @param articleId
 */
const getPortalArticleContent = (articleId: number) => {
  portalArticleContent(articleId).then((res) => {
    articleContent.value = res
    nextTick(() => {
      const anchors: NodeListOf<HTMLHeadingElement> =
        mdPreview.value.$el.querySelectorAll('h1,h2,h3,h4,h5,h6')
      anchors.forEach((node: HTMLHeadingElement) => {
        const line: string | null = node.getAttribute('data-v-md-line')
        if (line != null) {
          node.id = `data-v-md-line-${parseInt(line)}`
        }
      })
      anchorStore.updateAnchorState(handleTranslateToc(anchors))
    })
  })
}

const handleCopyCodeSuccess = () => {
  Notification.success('复制成功')
}

const handleTranslateToc = (nodes: NodeListOf<HTMLHeadingElement>): AnchorState[] => {
  const toc: AnchorState[] = []
  const stack: AnchorState[] = []

  nodes.forEach((node) => {
    const level = parseInt(node.tagName[1], 10)
    const line: string | null = node.getAttribute('data-v-md-line')
    const item: AnchorState = {
      level: level,
      lineIndex: line != null ? parseInt(line) : 0,
      title: node.innerText,
      children: []
    }

    // 如果 stack 是空的，说明这是第一个元素，直接加入 toc
    if (stack.length === 0) {
      toc.push(item)
      stack.push(item)
    } else {
      // 栈不为空时，找到合适的位置插入当前 header
      while (stack.length > 0 && stack[stack.length - 1].level >= level) {
        stack.pop() // 弹出比当前标题级别大或相同的标题
      }

      if (stack.length === 0) {
        toc.push(item) // 如果栈空了，表示这是新一层的顶级标题
      } else {
        stack[stack.length - 1].children.push(item)
      }
      stack.push(item)
    }
  })
  return toc
}
</script>
<style scoped></style>
