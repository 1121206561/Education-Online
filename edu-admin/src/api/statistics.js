import request from '@/utils/request'

export default {
  addStatisticsDaily(day) {
    return request({
      //后端接口的Controller的访问地址
      url: 'http://localhost:8891/edu/statistics-daily/addStatisticsDaily/' + day,
      //访问方式
      method: 'post',
    })
  },
  getStatisticsCount(type, begin, end) {
    return request({
      //后端接口的Controller的访问地址
      url: 'http://localhost:8891/edu/statistics-daily/getStatisticsCount/' + type + '/' + begin + '/' + end,
      //访问方式
      method: 'post',
    })
  },
}
