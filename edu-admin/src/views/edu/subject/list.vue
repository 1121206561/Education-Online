<template>
  <div class="app-container">
    <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:30px;"/>
    <el-button type="text" @click="append(null)">Append One Itemize</el-button>
    <el-tree
      ref="subjectTree"
      :data="subjectList"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree">
    <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <span>
          <el-button
            type="text"
            size="mini"
            v-if="node.level == 1"
            @click="() => append(data)">
            Append
          </el-button>
          <el-button
            type="text"
            size="mini"
            @click="() => remove(node, data)">
            Delete
          </el-button>
        </span>
      </span>
    </el-tree>
  </div>
</template>

<script>
  import subject from '@/api/subject'

  export default {
    data() {
      return {
        filterText: '',
        subjectList: [],
        defaultProps: {
          children: 'subjectTwoVoList',
          label: 'title'
        }
      }
    },
    watch: {
      filterText(val) {
        this.$refs.subjectTree.filter(val)
      }
    },
    created() {
      this.fetchNodeList()
    },
    methods: {
      //添加分类弹框功能
      append(data) {
        this.$prompt('请输入分类', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
        }).then(({value}) => {
          console.log(data)
          if (data !== null) {
            return subject.appendTwoSubject(value, data.id)
          } else {
            return subject.appendSubject(value)
          }
        }).then(() => {
          this.fetchNodeList()
          this.$message({
            type: 'success',
            message: '添加成功'
          })
        }).catch((response) => {
          if (response === 'cancel') {
            this.$message({
              type: 'info',
              message: '取消输入'
            })
          }
        })
      },
      //删除功能
      remove(node, data) {
        this.$confirm('此操作将永久删除该分类, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {   //点击确定执行这个
          return subject.removeSubject(data.id)//return表示后面的  .then()方法继续执行  进行删除操作
        }).then(() => {
          this.fetchNodeList()
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }).catch((response) => {    //点击取消执行这个
          if (response === 'cancel') {
            this.$message({
              type: 'info',
              message: '已取消删除'
            })
          }
        })
      }
      ,
      fetchNodeList() {
        subject.getSubjectList().then(response => {
          if (response.success === true) {
            this.subjectList = response.data.items
          } else {
            this.$message({
              type: 'error',
              message: '查询失败'
            })
          }
        }).catch(() => {
          this.$message({
            type: 'error',
            message: '查询失败'
          })
        })
      }
      ,
      filterNode(value, data) {
        if (!value) return true
        return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1
      }
    }
  }
</script>
