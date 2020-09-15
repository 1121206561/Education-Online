<template>
  <div class="app-container">
    <!--表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="日期">
        <el-date-picker
          v-model="day"
          type="date"
          placeholder="选择要统计的日期"
          value-format="yyyy-MM-dd"/>
      </el-form-item>
      <el-button
        :disabled="btnDisabled"
        type="primary"
        @click="createDate()">生成
      </el-button>
    </el-form>
  </div>
</template>
<script>
  import statistics from '@/api/statistics'

  export default {
    data() {
      return {
        day: ''
      }
    },
    created() {
    },
    methods: {
      createDate() {
        statistics.addStatisticsDaily(this.day)
          .then(() => {
            this.$message({
              type: 'success',
              message: '生成成功'
            })
          })
          .catch(() => {
            this.$message({
              type: 'error',
              message: '生成失败'
            })
          })
      }
    }
  }
</script>
