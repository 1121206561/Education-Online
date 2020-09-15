import request from '@/utils/request'

export default {
  getPageCourse(page, limit) {
    return request({
      url: '/edu/frontCourse/getPageCourse/' + page + '/' + limit,
      method: 'post'
    })
  },
  getCourseAll(id) {
    return request({
      url: '/edu/frontCourse/getCourseAll/' + id,
      method: 'post'
    })
  }
}
