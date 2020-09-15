<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name" style="width: 11%;"/>
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right" min="0"/>
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.intro"/>
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.career" :rows="10" type="textarea"/>
      </el-form-item>

      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">
        <!-- 头衔缩略图 -->
        <pan-thumb :image="teacher.avatar"/>
        <!-- 文件上传按钮 -->
        <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像</el-button>
        <!--
             -show：是否显示上传组件
             :key：类似于id，如果一个页面多个图片上传控件，可以做区分
             :url：后台上传的url地址
             @close：关闭上传组件
             @crop-upload-success：上传成功后的回调 -->
        <image-cropper
          v-show="imagecropperShow"
          :width="300"
          :height="300"
          :key="imagecropperKey"
          :url="'http://localhost:8888/edu/teacher/cos/img'"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"/>
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import teacher from '@/api/teacher'
  import ImageCropper from '@/components/ImageCropper'
  import PanThumb from '@/components/PanThumb'

  //将具体参数封装起来方便数据清空
  const defaultTeacher = {
    //对象中的具体参数
    name: '',
    sort: 0,
    level: '',
    career: '',
    intro: '',
    avatar: 'http://xueyuan-edu-1301453407.cos.ap-chengdu.myqcloud.com/2020/03/2622637f72-990d-437a-8232-e8e1a40e7fbc.png?sign=q-sign-algorithm%3Dsha1%26q-ak%3DAKID3uWnAHGl7G9rMbCNsSpxsZ1D9pIYtIm0%26q-sign-time%3D1585202297%3B1765202297%26q-key-time%3D1585202297%3B1765202297%26q-header-list%3D%26q-url-param-list%3D%26q-signature%3D868a4c3b67241cbd9e491f47fb0673d2e9c6ab7c',
  }

  export default {
    //声明引入的额外组件 , 如果不声明则不会调用其中<div class="">
    components: {ImageCropper, PanThumb},
    data() {
      return {
        //传值的对象
        teacher: defaultTeacher,
        imagecropperKey: 0,
        imagecropperShow: false
      }
    },
    //监听路由 , 如果路由发生变化就会执行具体方法
    watch: {
      $route(to, from) {   /*对路由跳转link-to方法监听*/
        this.init()
      }
    },
    created() {
      this.init()
    },
    methods: {
      //点击关闭按钮执行操作 隐藏弹出框
      close() {
        this.imagecropperShow = false
        this.imagecropperKey = this.imagecropperKey + 1
      },
      //点击保存按钮执行操作 隐藏弹出框 并且回显头像
      cropSuccess(response) {
        console.log(response)
        this.imagecropperShow = false
        this.teacher.avatar = response.images
        this.imagecropperKey = this.imagecropperKey + 1
      },
      //加载页面进行初始化操作封装成一个方法
      init() {
        if (this.$route.params && this.$route.params.id) {  //判断是否有id传过来
          const id = this.$route.params.id
          this.getTeacherById(id)
        } else {
          //数据清空
          this.teacher = {...defaultTeacher}
        }
      },
      //讲师添加和修改方法
      saveOrUpdate() {
        if (!this.teacher.id) {
          this.saveTeacher()
        } else {
          this.updateTeacher(this.teacher.id)
        }
      }
      ,
      //添加讲师
      saveTeacher() {
        this.$confirm('此操作将保存信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          return teacher.addTeacher(this.teacher)
        }).then(() => {
          this.$message({
            type: 'success',
            message: '保存成功!'
          })
          this.$router.push({path: '/edu/teacher'})  //跳转回list页面
        })
          .catch(() => {
            this.$message({
              type: 'error',
              message: '保存失败!'
            })
          })
      }
      ,
      //根据id查询讲师
      getTeacherById(id) {
        teacher.getTeacherById(id)
          .then((response) => {
            this.teacher = response.data.teacher
          })
          .catch(() => {
            this.$message({
              type: 'error',
              message: '获取信息失败!'
            })
          })
      }
      ,
      //更新讲师
      updateTeacher() {
        this.$confirm('此操作将保存信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          return teacher.updateTeacher(this.teacher)
        }).then(() => {
          this.$message({
            type: 'success',
            message: '更新成功!'
          })
          this.$router.push({path: '/edu/teacher'})  //跳转回list页面
        })
          .catch(() => {
            this.$message({
              type: 'error',
              message: '更新失败!'
            })
          })
      }
    }
  }
</script>
