import request from '@/utils/request'

export default {
  addCourse(courseInfo) {
    return request({
      //后端接口的Controller的访问地址
      url: '/edu/course/addCourse',
      //访问方式
      method: 'post',
      data: courseInfo
    })
  },
  getAllTeacher() {
    return request({
      //后端接口的Controller的访问地址
      url: '/edu/teacher/getAll',
      //访问方式
      method: 'get',
    })
  },
  getCourseById(id) {
    return request({
      //后端接口的Controller的访问地址
      url: '/edu/course/getCourseById/' + id,
      //访问方式
      method: 'post',
    })
  },
  updateCourse(courseInfo) {
    return request({
      //后端接口的Controller的访问地址
      url: '/edu/course/updateCourseById/' + courseInfo.id,
      //访问方式
      method: 'post',
      data: courseInfo
    })
  },
  getAllCourse(page, limit, searchObj) {
    return request({
      //后端接口的Controller的访问地址
      url: '/edu/course/getAllCourse/' + page + '/' + limit,
      //访问方式
      method: 'post',
      data: searchObj
    })
  },
  removeCourse(id) {
    return request({
      //后端接口的Controller的访问地址
      url: '/edu/course/removeCourse/' + id,
      //访问方式
      method: 'post',
    })
  }
}
