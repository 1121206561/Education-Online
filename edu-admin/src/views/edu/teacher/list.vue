<template>
  <div class="app-container">
    <!-- From表单  顶部查询套件-->
    <el-form :inline="true" :model="searchObj" class="demo-form-inline">
      <el-form-item label="姓名">
        <el-input v-model="searchObj.name" placeholder="姓名"></el-input>
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="searchObj.level" placeholder="讲师头衔">
          <el-option label="高级讲师" value="1"></el-option>
          <el-option label="首席讲师" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="添加时间">
        <el-date-picker
          v-model="searchObj.beginTime"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="searchObj.endTime"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getTeacherList()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row>  <!--对数据进行显示  :data="list"表示对数据进行遍历-->
      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="名称" width="80"/>
      <el-table-column label="头衔" width="80">
        <template slot-scope="scope">
          {{ scope.row.level===1?'高级讲师':'首席讲师' }}    <!--  === 代表严格判断 判断值和类型  -->
        </template>
      </el-table-column>
      <el-table-column prop="intro" label="资历"/>
      <el-table-column prop="gmtCreate" label="添加时间" width="160"/>
      <el-table-column prop="sort" label="排序" width="60"/>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/edu/teacher/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页功能-->
    <el-pagination
      background
      style="padding: 30px 0; text-align: center;"
      layout="prev, pager, next,jumper"
      :current-page="page"
      :page-size="limit"
      :total="total"
      @current-change="getTeacherList">
    </el-pagination>

  </div>
</template>

<script>
  import teacher from '@/api/teacher'

  export default {
    //定义变量的初始值
    data() {
      return {
        listLoading: true, // 是否显示loading信息
        list: null, // 数据列表
        total: 0, // 总记录数
        page: 1, // 页码
        limit: 10, // 每页记录数
        searchObj: {}// 查询条件
      }
    },
    //定义调用的方法
    created() {
      this.getTeacherList()
    },
    //调用的方法的具体实现
    methods: {
      getTeacherList(page = 1) {
        this.page = page
        this.listLoading = true
        teacher.getTeacherList(this.page, this.limit, this.searchObj)
          .then(response => {
            this.list = response.data.items   //将查询出来的数据存入变量中
            this.total = response.data.total
            this.listLoading = false
          })
          .catch(response => {
            console.log(response)
          })
      },
      resetData() {
        /*清空查询条件*/
        this.searchObj = {}
        this.getTeacherList()
      },
      //删除功能的实现
      removeDataById(id) {
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {   //点击确定执行这个
          return teacher.deleteTeacher(id)//return表示后面的  .then()方法继续执行  进行删除操作
        }).then(() => {
          this.getTeacherList()
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }).catch(() => {    //点击取消执行这个
          if (response === 'cancel') {
            this.$message({
              type: 'info',
              message: '已取消删除'
            })
          } else {
            this.$message({
              type: 'error',
              message: '删除失败'
            })
          }
        })
      }
    }
  }
</script>
