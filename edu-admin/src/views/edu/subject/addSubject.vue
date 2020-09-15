<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="信息描述">
        <el-tag type="info">excel模版说明</el-tag>
        <el-tag>
          <i class="el-icon-download"/>
          <a :href="'/static/execl模板.xlsx'">点击下载模版</a>
        </el-tag>
      </el-form-item>
      <el-form-item label="选择Excel">
        <el-upload
          ref="upload"
          :auto-upload="false"
          :on-success="fileUploadSuccess"
          :on-error="fileUploadError"
          :disabled="importBtnDisabled"
          :limit="1"
          :action="'http://localhost:8888/edu/subject/addSubject'"
          name="multipartFile"
          accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button
            :loading="loading"
            style="margin-left: 10px;"
            size="small"
            type="success"
            @click="submitUpload">{{ '上传到服务器' }}
          </el-button>
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>


<script>
  export default {
    data() {
      return {
        loading: false,
        importBtnDisabled: false //按钮是否禁用
      }
    },
    created() {

    },
    methods: {
      //成功回调的方法
      fileUploadSuccess(response) {
        if (response.code === 20000) {
          this.loading = false;
          this.$message({
            type: 'success',
            message: '上传成功!'
          })
        } else {
          this.loading = false;
          const messages = response.data.items
          let msgString = '<ul>'
          messages.forEach(msg => {
            msgString += `<li>${msg}</li>`
          })
          msgString += '</ul>'
          this.$alert(msgString, {
            dangerouslyUseHTMLString: true
          })
        }
      },
      //失败回调的方法  例如路径错误等原因没有访问到接口则报这个错
      fileUploadError() {
        this.loading = false
        this.$message({
          type: 'error',
          message: '导入失败'
        })
      },
      //上传表格的具体方法
      submitUpload() {
        this.importBtnDisabled = true
        this.loading = true
        //表单提交
        this.$refs.upload.submit()
      }
    }
  }
</script>
