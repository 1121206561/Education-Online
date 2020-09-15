import request from '@/utils/request'

export default {
  getVideoId(id) {
    return request({
      url: '/edu/frontVideo/getVideoId/' + id,
      method: 'post'
    })
  }
}
