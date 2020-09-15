import request from '@/utils/request'

export default {
  getInfo(token) {
    return request({
      url: 'http://localhost:8890/edu/ucenter-member/getInfo/' + token,
      method: 'get'
    })
  }
}
