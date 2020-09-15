<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>
    <el-steps :active="3" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>

    <div class="ccInfo">
      <img :src="coursePublish.cover">
      <div class="main">
        <h2>{{ coursePublish.title }}</h2>
        <p class="gray"><span>共{{ coursePublish.lessonNum }}课时</span></p>
        <p><span>所属分类：{{ coursePublish.subjectLevelOne }} — {{ coursePublish.subjectLevelTwo }}</span></p>
        <p>课程讲师：{{ coursePublish.teacherName }}</p>
        <h3 class="red">￥{{ coursePublish.price }}</h3>
        <p>课程描述：{{ coursePublish.description }}</p>
      </div>
    </div>
    <div>
      <el-button @click="previous" style="margin-left: 50%">返回修改</el-button>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="publish">发布课程</el-button>
    </div>
  </div>
</template>
<script>
  import release from '@/api/release'

  export default {
    data() {
      return {
        id: '',
        saveBtnDisabled: false,
        coursePublish: []
      }
    },
    created() {
      this.init()
      this.getAllCourseByAll()
    },
    methods: {
      init() {
        if (this.$route.params && this.$route.params.id) {
          this.id = this.$route.params.id
        }
      },
      //查询课程的所有需要信息
      getAllCourseByAll() {
        release.getAllCourseByAll(this.id)
          .then((response) => {
            this.coursePublish = response.data.items
          })
      },
      previous() {
        this.$router.push({path: '/edu/course/perfect/' + this.id})
      },
      publish() {
        release.publishCourse(this.id)
          .then(() => {
            this.$message({
              type: 'success',
              message: '发布成功!'
            })
            this.$router.push({path: '/edu/course'})
          })
      }
    }
  }
</script>
<style scoped>
  .ccInfo {
    background: #f5f5f5;
    padding: 20px;
    overflow: hidden;
    border: 1px dashed #DDD;
    margin-bottom: 40px;
    position: relative;
  }

  .ccInfo img {
    background: #d6d6d6;
    width: 500px;
    height: 278px;
    display: block;
    float: left;
    border: none;
  }

  .ccInfo .main {
    margin-left: 520px;
  }

  .ccInfo .main h2 {
    font-size: 28px;
    margin-bottom: 30px;
    line-height: 1;
    font-weight: normal;
  }

  .ccInfo .main p {
    margin-bottom: 10px;
    word-wrap: break-word;
    line-height: 24px;
    max-height: 48px;
    overflow: hidden;
  }

  .ccInfo .main p {
    margin-bottom: 10px;
    word-wrap: break-word;
    line-height: 24px;
    max-height: 48px;
    overflow: hidden;
  }

  .ccInfo .main h3 {
    left: 540px;
    bottom: 20px;
    line-height: 0;
    font-size: 28px;
    color: #d32f24;
    font-weight: normal;
    position: absolute;
  }
</style>
