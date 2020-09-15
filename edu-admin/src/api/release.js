import request from '@/utils/request'

export default {
  getAllCourseByAll(id) {
    return request({
      //后端接口的Controller的访问地址
      url: '/edu/course/getAllCourseByAll/' + id,
      //访问方式
      method: 'post',
    })
  },
  publishCourse(id){
    return request({
      //后端接口的Controller的访问地址
      url: '/edu/course/publishCourse/' + id,
      //访问方式
      method: 'post',
    })
  }
}
