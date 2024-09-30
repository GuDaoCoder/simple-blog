<template>
  <tiny-dialog-box
    :visible="visible"
    :title="title"
    :width="limit == 1 ? '400px' : '690px'"
    :show-close="false"
    destroy-on-close
  >
    <tiny-file-upload
      ref="uploadImageRef"
      drag
      :action="action"
      paste-upload
      is-hidden
      list-type="picture-card"
      :multiple="limit > 1"
      :accept="accept"
      :limit="limit"
      :file-size="[0, 10240]"
      :auto-upload="false"
      :data="{ module }"
      @success="handleUploadSuccess"
      @error="handleUploadError"
    >
      <tiny-icon-fileupload class="tiny-svg-size icon-fileupload"></tiny-icon-fileupload>
    </tiny-file-upload>
    <template #footer>
      <tiny-button type="primary" @click="handleUpload">上传</tiny-button>
      <tiny-button type="info" @click="handleClear">清空</tiny-button>
      <tiny-button type="info" @click="emit('close')">关闭</tiny-button>
    </template>
  </tiny-dialog-box>
</template>

<script setup lang="ts">
import { iconFileupload } from '@opentiny/vue-icon'
import { ref } from 'vue'
import { notifyError, notifySuccess } from '@utils/notify'

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
      notifySuccess('上传成功')
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
/**
 * 上传
 */

const handleUpload = () => {
  uploadLoading.value = true
  uploadImageRef.value.submit()
}

/**
 * 上传成功
 */
const handleUploadSuccess = (res: ApiCommon.Result<ApiAttachment.UploadResponse>) => {
  uploadLoading.value = false
  emit('upload-success', res)
}

/**
 * 上传失败
 */
const handleUploadError = () => {
  uploadLoading.value = false
  notifyError('上传失败')
}
</script>

<style scoped lang="scss">
:deep(.tiny-dialog-box__body) {
  display: flex;
  justify-content: center;
}
</style>
