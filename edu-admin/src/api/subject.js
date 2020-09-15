import request from '@/utils/request'

export default {
  getSubjectList() {
    return request({
      //后端接口的Controller的访问地址
      url: '/edu/subject/list',
      //访问方式
      method: 'post',
    })
  },
  removeSubject(id) {
    return request({
      //后端接口的Controller的访问地址
      url: '/edu/subject/removeSubject/' + id,
      //访问方式
      method: 'delete',
    })
  },
  appendSubject(value) {
    return request({
      //后端接口的Controller的访问地址
      url: '/edu/subject/appendSubject/' + value,
      //访问方式
      method: 'get',
    })
  },
  appendTwoSubject(value, id) {
    return request({
      //后端接口的Controller的访问地址
      url: '/edu/subject/appendTwoSubject/' + value + '/' + id,
      //访问方式
      method: 'get',
    })
  }
}
