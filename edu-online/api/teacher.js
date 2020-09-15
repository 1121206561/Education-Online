import request from '@/utils/request'

export default {
  getPageTeacher(page, limit) {
    return request({
      url: '/edu/frontTeacher/getPageTeacher/' + page + '/' + limit,
      method: 'post'
    })
  },
  getTeacherById(id){
    return request({
      url: '/edu/frontTeacher/getTeacherById/' + id,
      method: 'post'
    })
  }
}
