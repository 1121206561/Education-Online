import request from '@/utils/request'

export default {
  addVideoById(video, courseId) {
    return request({
      //后端接口的Controller的访问地址
      url: '/edu/video/addVideoById/' + courseId,
      //访问方式
      method: 'post',
      data: video
    })
  },
  updateVideoById(video, videoId) {
    return request({
      //后端接口的Controller的访问地址
      url: '/edu/video/updateVideoById/' + videoId,
      //访问方式
      method: 'post',
      data: video
    })
  },
  removeVideoById(id) {
    return request({
      //后端接口的Controller的访问地址
      url: '/edu/video/removeVideoById/' + id,
      //访问方式
      method: 'post',
    })
  },
  uploadVideo() {
    return request({
      //后端接口的Controller的访问地址
      url: 'http://localhost:8887/edu/vid/sign',
      //访问方式
      method: 'post',
    })
  },
  deleteVid(videoUrl) {
    return request({
    })
  }
}
