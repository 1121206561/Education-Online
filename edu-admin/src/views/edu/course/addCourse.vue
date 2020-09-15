<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>
    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>
    <el-form label-width="120px">
      <el-form-item label="课程标题">
        <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
      </el-form-item>
      <!-- 所属分类 TODO -->
      <!-- 所属分类：级联下拉列表 -->
      <!-- 一级分类 -->
      <el-form-item label="课程类别">
        <el-select
          v-model="courseInfo.subjectIdOne"
          placeholder="请选择" @change="subjectLevelTwoChanged">
          <el-option
            v-for="subject in subjectOneList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
        <!-- 二级分类 -->
        <el-select v-model="courseInfo.subjectId" placeholder="请选择">
          <el-option
            v-for="subject in subjectTwoList"
            :key="subject.value"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
      </el-form-item>
      <!-- 课程讲师 TODO -->
      <!-- 课程讲师 -->
      <el-form-item label="课程讲师">
        <el-select
          v-model="courseInfo.teacherId"
          placeholder="请选择">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"/>
        </el-select>
      </el-form-item>

      <el-form-item label="总课时">
        <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
      </el-form-item>
      <!-- 课程简介-->
      <el-form-item label="课程简介">
        <tinymce :height="300" v-model="courseInfo.description"/>
      </el-form-item>

      <!-- 课程封面 TODO -->
      <!-- 课程封面-->
      <el-form-item label="课程封面">
        <el-upload
          action="http://localhost:8888/edu/teacher/cos/img?host=cover"
          list-type="picture-card"
          :on-success="handleAvatarSuccess"
          :on-error="handleAvatarError"
          :hidden=saveImages
          :on-remove="handleRemove">
          <i class="el-icon-plus"></i>
        </el-upload>
        <img :src="courseInfo.cover" style="width: 20%;">
      </el-form-item>
      <el-form-item label="课程价格">
        <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/>
      </el-form-item>
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">保存并下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import course from '@/api/course'
  import subject from '@/api/subject'
  import Tinymce from '@/components/Tinymce'

  export default {
    components: {Tinymce},
    data() {
      return {
        saveBtnDisabled: false,
        saveImages: false,
        teacherList: [],
        subjectOneList: [],
        subjectTwoList: [],
        courseInfo: {
          title: '',
          lessonNum: 0,
          price: 0,
          teacherId: '',
          subjectId: '',
          cover: '0',
          description: ''
        }
      }
    },
    watch: {
      $route(to, from) {   /*对路由跳转link-to方法监听*/
        this.init()
      }
    },
    created() {
      this.getOneSubject();
      this.init();
      this.getAllTeacher()
    },
    methods: {
      //如果点击的是上一步则进行数据回显并进行更新操作
      init() {
        if (this.$route.params && this.$route.params.id) {
          const id = this.$route.params.id;
          this.getCourseById(id)
          this.saveImages = true
        } else {
          this.courseInfo.title = '';
          this.courseInfo.lessonNum = 0;
          this.courseInfo.price = 0;
          this.courseInfo.teacherId = '';
          this.courseInfo.subjectId = '';
          this.courseInfo.cover = '';
          this.courseInfo.description = '';
          this.courseInfo.subjectIdOne = ''
        }
      },
      //图片上传成功后的回调
      handleAvatarSuccess(response) {
        this.courseInfo.cover = response.data.images
        this.saveImages = true
      },
      //图片上传失败后的回调
      handleAvatarError() {
        this.$message({
          type: 'error',
          message: '上传图片失败!'
        });
      },
      //重新上传图片
      updateImages() {
        this.saveImages = false
        this.courseInfo.cover = ''
      },
      //查询所有1级分类
      getOneSubject() {
        subject.getSubjectList()
          .then((response) => {
            this.subjectOneList = response.data.items
            for (let i = 0; i < this.subjectOneList.length; i++) {
              if (this.courseInfo.subjectIdOne === this.subjectOneList[i].id) {
                this.subjectTwoList = this.subjectOneList[i].subjectTwoVoList
              }
            }
          })
      },
      //根据一级分类查询所有二级分类
      subjectLevelTwoChanged(value) {
        //遍历循环所有一级分类
        for (let i = 0; i < this.subjectOneList.length; i++) {
          //得到每个一级对象的id 并判断是否是选择的id
          if (value === this.subjectOneList[i].id) {
            //如果是则得到二级分类
            this.subjectTwoList = this.subjectOneList[i].subjectTwoVoList
            this.courseInfo.subjectId = '';
          }
        }
      },
      //根据id查询课程信息
      getCourseById(id) {
        course.getCourseById(id)
          .then((response) => {
            this.courseInfo = response.data.items
            this.courseInfo.subjectIdOne = response.data.parentId
          }).catch(() => {
          this.$message({
            type: 'error',
            message: '查询课程失败!'
          });
        })
      },
      //得到所有老师的id信息
      getAllTeacher() {
        course.getAllTeacher()
          .then((response) => {
            this.teacherList = response.data.items;
          }).catch(() => {
          this.$message({
            type: 'error',
            message: '查询老师失败!'
          });
        })
      },
      //下一步操作
      next() {
        if (!this.courseInfo.id) {
          //添加操作
          course.addCourse(this.courseInfo)
            .then((response) => {
              this.$message({
                type: 'success',
                message: '添加成功!'
              });
              this.$router.push({path: '/edu/course/perfect/' + response.data.id})
            }).catch(() => {
            this.$message({
              type: 'error',
              message: '添加失败!'
            })
          })
        } else {
          //更新操作
          course.updateCourse(this.courseInfo)
            .then(() => {
              this.$message({
                type: 'success',
                message: '更新成功!'
              });
              this.$router.push({path: '/edu/course/perfect/' + this.courseInfo.id})
            }).catch(() => {
            this.$message({
              type: 'error',
              message: '更新失败!'
            })
          })
        }
      }
    }
  }
</script>
<!--scoped代表当前页面有效-->
<style scoped>
  .tinymce-container {
    line-height: 29px;
  }
</style>
