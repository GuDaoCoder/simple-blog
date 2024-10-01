import dayjs from 'dayjs'

export const formatDate = (datetime: string, newFormat: string) => {
  return datetime && newFormat ? dayjs(datetime).format(newFormat) : ''
}
