<template>
  <div>
    <link href="https://imgcache.qq.com/open/qcloud/video/tcplayer/tcplayer.css" rel="stylesheet">
    <!--如果需要在 Chrome 和 Firefox 等现代浏览器中通过 H5 播放 HLS 格式的视频，需要在 tcplayer.v4.min.js 之前引入 hls.js。-->
    <script src="https://imgcache.qq.com/open/qcloud/video/tcplayer/libs/hls.min.0.12.4.js"></script>
    <!--播放器脚本文件-->
    <script src="https://imgcache.qq.com/open/qcloud/video/tcplayer/tcplayer.v4.min.js"></script>
    <video id="player-container-id" width="1950" height="700" preload="auto" playsinline webkit-playsinline></video>
  </div>
</template>
<script>
  import vod from '@/api/video'

  export default {
    layout: 'video',
    asyncData({params, error}) {
      return vod.getVideoId(params.vid).then(response => {
        return {
          videoId: response.data.data.item
        }
      })
    },
    /**
     * 页面渲染完成时：此时js脚本已加载，Aliplayer已定义，可以使用
     * 如果在created生命周期函数中使用，Aliplayer is not defined错误
     */
    mounted() {
      var player = TCPlayer('player-container-id', { // player-container-id 为播放器容器 ID，必须与 html 中一致
        fileID: this.videoId, // 请传入需要播放的视频 filID（必须）
        appID: '1301453407',// 请传入点播账号的 appID（必须）
        plugins:{
          ContinuePlay: { // 开启续播功能
             auto: true, //[可选] 是否在视频播放后自动续播
             text:'上次播放至 ', //[可选] 提示文案
             btnText: '恢复播放' //[可选] 按钮文案
          },
          ProgressMarker: true
        },
        playbackRates: [0.5,1,1.25,1.5,2] // 设置变速播放倍率选项，仅 HTML5 播放模式有效
      });
    }
  }

</script>
