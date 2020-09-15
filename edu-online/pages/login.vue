<template>
  <div class="main">
    <div class="title">
      <a class="active" href="/login">登录</a>
      <span>·</span>
      <a href="/register">注册</a>
    </div>
    <div class="sign-up-container">
      <form action="register">
        <div class="input-prepend restyle">
          <input type="text" placeholder="手机号">
          <i class="iconfont icon-phone"/>
        </div>
        <div class="input-prepend">
          <input type="password" placeholder="密码">
          <i class="iconfont icon-password"/>
        </div>
        <div class="btn">
          <input type="submit" class="sign-in-button" value="登录">
        </div>
      </form>
      <!-- 更多登录方式 -->
      <div class="more-sign">
        <h6>社交帐号登录</h6>
        <ul>
          <li><a id="weibo" class="weibo" target="_blank" href="http://localhost:8890/api/ucenter/wb/login"><img style="width: 100%;" src="https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1586742607&di=8e7df3fb2c935dc95c118e24b1e235d5&src=http://img.jk51.com/img_jk51/349933313.jpeg"></a></li>
          <li><a id="weixin" class="weixin" target="_blank" href="#"></a></li>
          <li><a id="qq" class="qq" target="_blank" href="#"></a></li>
        </ul>
      </div>
    </div>
  </div>
</template>
<script>
  import '~/assets/css/sign.css'
  export default {
    layout: 'login'
  }
</script>
