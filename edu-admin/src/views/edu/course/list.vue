<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <!-- 所属分类：级联下拉列表 -->
      <!-- 一级分类 -->
      <el-form-item label="课程类别">
        <el-select
          v-model="searchObj.subjectParentId"
          placeholder="请选择"
          @change="subjectLevelTwoChanged">
          <el-option
            v-for="subject in subjectOneList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
        <!-- 二级分类 -->
        <el-select v-model="searchObj.subjectId" placeholder="请选择">
          <el-option
            v-for="subject in subjectTwoList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
      </el-form-item>
      <!-- 标题 -->
      <el-form-item>
        <el-input v-model="searchObj.title" placeholder="课程标题"/>
      </el-form-item>
      <!-- 讲师 -->
      <el-form-item>
        <el-select
          v-model="searchObj.teacherId"
          placeholder="请选择讲师">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"/>
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row
      row-class-name="myClassList">
      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="课程信息" width="470" align="center">
        <template slot-scope="scope">
          <div class="info">
            <div class="pic">
              <img :src="scope.row.cover" alt="scope.row.title" width="150px">
            </div>
            <div class="title">
              <a href="">{{ scope.row.title }}</a>
              <p>{{ scope.row.lessonNum }}课时</p>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center">
        <template slot-scope="scope">
          {{ scope.row.gmtCreate.substr(0, 10) }}
        </template>
      </el-table-column>
      <el-table-column label="发布时间" align="center">
        <template slot-scope="scope">
          {{ scope.row.gmtModified.substr(0, 10) }}
        </template>
      </el-table-column>
      <el-table-column label="价格" width="100" align="center">
        <template slot-scope="scope">
          {{ Number(scope.row.price) === 0 ? '免费' :
          '¥' + scope.row.price.toFixed(2) }}
        </template>
      </el-table-column>
      <el-table-column prop="buyCount" label="付费学员" width="100" align="center">
        <template slot-scope="scope">
          {{ scope.row.buyCount }}
        </template>
      </el-table-column>
      <el-table-column prop="status" label="课程状态" width="100" align="center" >
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === 'Draft' ? 'primary' : 'success'">{{scope.row.status === 'Draft' ? '未发布' : '已发布'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <router-link :to="'/edu/course/info/'+scope.row.id">
            <el-button type="text" size="mini" icon="el-icon-edit">编辑课程信息</el-button>
          </router-link>
          <router-link :to="'/edu/course/chapter/'+scope.row.id">
            <el-button type="text" size="mini" icon="el-icon-edit">编辑课程大纲</el-button>
          </router-link>
          <el-button type="text" size="mini" icon="el-icon-delete" @click="removeCourse(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getAll"
    />

  </div>
</template>

<script>
  import course from '@/api/course'
  import subject from '@/api/subject'

  export default {
    data() {
      return {
        listLoading: true,
        page: 1,
        total: 0,
        limit: 10,
        list: null,
        teacherList: [],
        subjectOneList: [],
        subjectTwoList: [],
        searchObj: {
          subjectId: '',
          subjectParentId: '',
          title: '',
          teacherId: ''
        }
      }
    },
    created() {
      this.getAll()
      this.getAllTeacher()
      this.getOneSubject()
    },
    methods: {
      //删除课程操作,包括删除该课程的描述和张杰
      removeCourse(id) {
        this.$confirm('此操作将删除课程, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'info'
        }).then(() => {
          return this.$confirm('此操作将会造成不可逆的结果, 是否仍然继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })
        }).then(() => {
          return course.removeCourse(id)
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.getAll()  //刷新list页面
        }).catch(() => {
          this.$message({
            type: 'error',
            message: '删除失败!'
          })
        })
      },
      //清空查询条件
      resetData() {
        this.searchObj.subjectId = '';
        this.searchObj.subjectParentId = '';
        this.searchObj.title = '';
        this.searchObj.teacherId = ''
        this.getAll();
      }
      ,
      //添加查询条件
      fetchData() {
        this.getAll()
      }
      ,
      //查询所有讲师
      getAllTeacher() {
        course.getAllTeacher()
          .then((response) => {
            this.teacherList = response.data.items
          }).catch(() => {
          this.$message({
            type: 'error',
            message: '查询讲师失败!'
          });
        })
      }
      ,
      //查询所有1级分类
      getOneSubject() {
        subject.getSubjectList()
          .then((response) => {
            this.subjectOneList = response.data.items
          })
      }
      ,
      //根据一级分类查询所有二级分类
      subjectLevelTwoChanged(value) {
        //遍历循环所有一级分类
        for (let i = 0; i < this.subjectOneList.length; i++) {
          //得到每个一级对象的id 并判断是否是选择的id
          if (value === this.subjectOneList[i].id) {
            //如果是则得到二级分类
            this.subjectTwoList = this.subjectOneList[i].subjectTwoVoList
            this.searchObj.subjectId = '';
          }
        }
      }
      ,
      getAll(page = 1) {
        this.page = page
        course.getAllCourse(this.page, this.limit, this.searchObj)
          .then((response) => {
            this.total = response.data.total
            this.list = response.data.items
            this.listLoading = false
          })
          .catch(() => {
            this.$message({
              type: 'error',
              message: '查询课程失败!'
            });
          })
      }
    }
  }
</script>
