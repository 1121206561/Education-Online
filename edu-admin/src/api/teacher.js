import request from '@/utils/request'
//分页查询方法
export default {
  //分页查询的三个参数
  getTeacherList(page, limit, searchObj) {
    return request({
      //后端接口的Controller的访问地址
      //es6 中的url表达式写法     `/edu/teacher/getPageTermAll/${page}/${limit}` 在最外面 ` `  里面写路径
      url: '/edu/teacher/getPageTermAll/' + page + '/' + limit,
      //访问方式
      method: 'post',
      //传递的数据的格式 如果是普通数据则是param  如果是json则是data 必须传递的是对象
      data: searchObj
    })
  },
  deleteTeacher(id) {
    return request({
      //后端接口的Controller的访问地址
      url: '/edu/teacher/deleteTeacher/' + id,
      //访问方式
      method: 'delete',
    })
  },
  addTeacher(teacher) {
    return request({
      //后端接口的Controller的访问地址
      url: '/edu/teacher/addTeacher/',
      //访问方式
      method: 'post',
      data: teacher
    })
  },
  updateTeacher(teacher) {
    return request({
      //后端接口的Controller的访问地址
      url: '/edu/teacher/updateTeacher/' + teacher.id,
      //访问方式
      method: 'post',
      data: teacher
    })
  },
  getTeacherById(id) {
    return request({
      //后端接口的Controller的访问地址
      url: '/edu/teacher/getAllById/' + id,
      //访问方式
      method: 'get',
    })
  }
}
