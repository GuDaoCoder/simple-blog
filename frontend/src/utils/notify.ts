import { Notify } from '@opentiny/vue'

export const notifySuccess = (msg: string) => {
  Notify({
    type: 'success',
    message: msg,
    position: 'top-right'
  })
}

export const notifyInfo = (msg: string) => {
  Notify({
    type: 'info',
    message: msg,
    position: 'top-right'
  })
}

export const notifyWarning = (msg: string) => {
  Notify({
    type: 'warning',
    message: msg,
    position: 'top-right'
  })
}

export const notifyError = (msg: string) => {
  Notify({
    type: 'error',
    message: msg,
    position: 'top-right'
  })
}
