<template>
  <a-modal
    :visible="visible"
    :title="title"
    :width="limit == 1 ? '400px' : '690px'"
    unmount-on-close
    @cancel="emit('close')"
  >
    <a-upload
      ref="uploadImageRef"
      draggable
      with-credentials
      image-preview
      :action="action"
      list-type="picture-card"
      :multiple="limit > 1"
      :accept="accept"
      :limit="limit"
      :file-size="[0, 10240]"
      :auto-upload="false"
      :data="{ module }"
      @success="handleUploadSuccess"
      @error="handleUploadError"
      @change="handleUploadChange"
    >
      <tiny-icon-fileupload class="tiny-svg-size icon-fileupload"></tiny-icon-fileupload>
    </a-upload>
    <template #footer>
      <a-space>
        <a-button type="primary" @click="handleUpload" :loading="uploadLoading"> 上传 </a-button>
        <a-button type="outline" @click="handleClear">清空</a-button>
      </a-space>
    </template>
  </a-modal>
</template>

<script setup lang="ts">
import { iconFileupload } from '@opentiny/vue-icon'
import { Notification, type FileItem } from '@arco-design/web-vue'

defineProps({
  visible: {
    type: Boolean,
    required: true
  },
  title: {
    type: String,
    default: '图片上传'
  },
  accept: {
    type: String,
    default: '.png,.jpeg,.jpg'
  },
  limit: {
    type: Number,
    default: 1
  },
  module: {
    type: String,
    required: true
  },
  success: {
    type: Function,
    default: () => {
      Notification.success('上传成功')
    }
  }
})

const emit = defineEmits(['upload-success', 'close'])

const TinyIconFileupload = iconFileupload()
const action = ref('/api/admin/attachments/upload')

const uploadImageRef = ref()
/**
 * 清空文件列表
 */
const handleClear = () => {
  uploadImageRef.value.clearFiles()
}

const uploadLoading = ref(false)

const currentFileList = ref<FileItem[]>([])

/**
 * 上传
 */
const handleUpload = () => {
  if (currentFileList.value.length === 0) {
    Notification.warning('请先上传文件')
  } else {
    uploadLoading.value = true
    uploadImageRef.value.submit()
  }
}
const handleUploadChange = (fileList: FileItem[]) => {
  currentFileList.value = fileList
}

/**
 * 上传成功
 */
const handleUploadSuccess = (fileItem: FileItem) => {
  uploadLoading.value = false
  emit('upload-success', fileItem.response)
}

/**
 * 上传失败
 */
const handleUploadError = () => {
  uploadLoading.value = false
  Notification.error('上传失败')
}
</script>

<style scoped lang="scss">
.arco-upload-wrapper {
  display: flex;
  justify-content: center;
}
</style>
