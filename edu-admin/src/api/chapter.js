import request from '@/utils/request'

export default {
  getChaptersByCourseId(courseId) {
    return request({
      //后端接口的Controller的访问地址
      url: '/edu/chapter/getChapterById/' + courseId,
      //访问方式
      method: 'post',
    })
  },
  addChapter(chapter, id) {
    return request({
      //后端接口的Controller的访问地址
      url: '/edu/chapter/addChapter/' + id,
      //访问方式
      method: 'post',
      data: chapter
    })
  },
  removeChapterById(value) {
    return request({
      //后端接口的Controller的访问地址
      url: '/edu/chapter/removeChapterById/' + value,
      //访问方式
      method: 'post',
    })
  },
  updateChapterById(chapter,courseId) {
    return request({
      //后端接口的Controller的访问地址
      url: '/edu/chapter/updateChapterById/' + courseId,
      //访问方式
      method: 'post',
      data: chapter
    })
  }
}

