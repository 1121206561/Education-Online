<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>
    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>
    <el-button type="text" style="font-size: 20px" @click="addChapterByIdTwo">添加章节</el-button>
    <!-- 章节 -->
    <ul class="chanpterList">
      <li
        v-for="chapter in chaptersList"
        :key="chapter.id">
        <p>
          {{ chapter.title }}
          <span class="acts">
                <el-button type="text" @click="addVideoByIdTwo(chapter.id)">添加课时</el-button>
                <el-button style="" type="text"
                           @click="updateChapterByIdTwo(chapter.title,chapter.id,chapter.sort)">编辑</el-button>
                <el-button type="text" @click="removeChapterById(chapter.id)">删除</el-button>
          </span>
        </p>
        <!-- 视频 -->
        <ul class="chanpterList videoList">
          <li
            v-for="video in chapter.videoVos"
            :key="video.id">
            <p>{{ video.title }}
              <span class="acts">
                        <el-button type="text"
                                   @click="updateVideoByIdTwo(video.title,video.id,video.sort,video.videoSourceId)">编辑</el-button>
                        <el-button type="text" @click="removeVideoById(video.id)">删除</el-button>
              </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>
    <el-button @click="previous">上一步</el-button>
    <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>

    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapter.title" placeholder="请按照 第X章：内容 输入章节标题"/>
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="UpdateOrAdd">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加小节">
      <el-form :model="video" label-width="120px">
        <el-form-item label="小节标题">
          <el-input v-model="video.title" placeholder="请按照 第X节：内容 输入小节标题"/>
        </el-form-item>
        <el-form-item label="小节排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
        </el-form-item>
        <input ref="filElem" type="file" @change="getFile" class="upload-file" hidden>
        <el-button type="primary" @click="change" style="margin-left: 10%">上传视频</el-button>
        {{this.uploaderInfos.videoUrl}}
        <el-button type="danger" icon="el-icon-delete" circle @click="deleteVid"></el-button>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="UpdateOrAddVideo">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<script>
  import chapter from "@/api/chapter"
  import video from "@/api/video"
  import TcVod from 'vod-js-sdk-v6'

  //获取上传视频所需要的地址
  function getSignature() {
    return video.uploadVideo().then(function (response) {
      return response.data.item;
    })
  };

  export default {
    components: {TcVod},
    data() {
      return {
        saveBtnDisabled: false,
        id: '',
        uploaderInfos: [],
        chaptersList: [],
        courseId: '',
        videoId: '',
        dialogChapterFormVisible: false, //是否显示章节表单
        dialogVideoFormVisible: false, //是否显示小节表单
        chapter: {// 章节对象
          title: '',
          sort: 0,
        },
        video: {// 小节对象
          title: '',
          sort: 0,
          chapterId: ''
        }
      }
    },
    created() {
      this.init()
      this.getChaptersByCourseId(this.id)
    },
    methods: {
      init() {
        if (this.$route.params && this.$route.params.id) {
          this.id = this.$route.params.id
        }
      },
      //删除已经上传的视频返回的id
      deleteVid() {
        this.uploaderInfos.videoUrl = ''
      },
      //选择视频文件
      change() {
        this.$refs.filElem.dispatchEvent(new MouseEvent('click'))
      },//文件上传
      getFile() {
        const inputFile = this.$refs.filElem.files[0];
        //如果不将值清空则不会触发change事件
        this.$refs.filElem.value = null;
        //alert(e.files[0].name);
        const tcVod = new TcVod({
          //上传位置
          getSignature: getSignature,
        });
        const uploader = tcVod.upload({
          mediaFile: inputFile, // 媒体文件（视频或音频或图片），类型为 File
          //是否上传声音
          allowAudio: 1,
        });
        var uploaderInfo = {
          videoUrl: '',
          cover: '',
        };
        this.uploaderInfos = uploaderInfo
        uploader.done().then(function (doneResult) {
          alert("上传成功");
          uploaderInfo.videoUrl = doneResult.fileId
        }).catch(function () {
          alert("上传失败");
        })
      },
      //删除小节
      removeVideoById(id) {
        this.$confirm('此操作将删除该小节, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          return video.removeVideoById(id)
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功'
          });
          this.getChaptersByCourseId(this.id)
        }).catch((response) => {
          if (response === 'cancel') {
            this.$message({
              type: 'info',
              message: '取消输入'
            });
          }
        })
      },
      //更新小节内容回显
      updateVideoByIdTwo(title, id, sort, videoSourceId) {
        this.video.sort = sort
        this.uploaderInfos.videoUrl = videoSourceId
        this.video.title = title
        this.video.id = id
        this.video.sort = sort
        this.dialogVideoFormVisible = true
      },
      //判断是更新还是新增操作
      UpdateOrAddVideo() {
        this.dialogVideoFormVisible = false
        if (this.video.id) {
          this.updateVideoById()
        } else {
          this.addVideoById()
        }
      },
      //更新小节操作
      updateVideoById() {
        this.video.videoSourceId = this.uploaderInfos.videoUrl
        this.videoId = this.video.id
        video.updateVideoById(this.video, this.videoId)
          .then(() => {
            this.$message({
              type: 'success',
              message: '更新成功'
            });
            this.getChaptersByCourseId(this.id)
          })
          .catch(() => {
            this.$message({
              type: 'error',
              message: '更新失败'
            });
          })
      },
      //添加小节操作数据清除
      addVideoByIdTwo(id) {
        this.uploaderInfos = []
        this.video.title = ''
        this.video.chapterId = id
        this.video.sort = 0
        this.video.id = ''
        this.dialogVideoFormVisible = true
      }
      ,
      //添加小节操作
      addVideoById() {
        this.video.videoSourceId = this.uploaderInfos.videoUrl
        console.log(this.video.videoUrl)
        video.addVideoById(this.video, this.id)
          .then(() => {
            this.$message({
              type: 'success',
              message: '添加成功'
            });
            this.getChaptersByCourseId(this.id)
          }).catch(() => {
          this.$message({
            type: 'error',
            message: '添加失败'
          });
        })
      }
      ,
      //添加操作数据清除
      addChapterByIdTwo() {
        this.chapter.title = ''
        this.chapter.id = ''
        this.chapter.sort = 0
        this.dialogChapterFormVisible = true
      }
      ,
      //更新操作的回显
      updateChapterByIdTwo(title, id, sort) {
        this.chapter.sort = sort
        this.chapter.title = title
        this.chapter.id = id
        this.dialogChapterFormVisible = true
      }
      ,
      //判断是更新还是添加操作
      UpdateOrAdd() {
        this.dialogChapterFormVisible = false
        if (this.chapter.id) {
          this.updateChapterById()
        } else {
          this.addChapter()
        }
      }
      ,
      //更新章节信息
      updateChapterById() {
        this.courseId = this.chapter.id
        chapter.updateChapterById(this.chapter, this.courseId)
          .then(() => {
            this.$message({
              type: 'success',
              message: '更新成功'
            });
            this.getChaptersByCourseId(this.id)
          })
          .catch(() => {
            this.$message({
              type: 'error',
              message: '更新失败'
            });
          })
      }
      ,
      //删除章节信息
      removeChapterById(value) {
        this.$confirm('此操作将删除该章节, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          return chapter.removeChapterById(value)
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功'
          });
          this.getChaptersByCourseId(this.id)
        }).catch((response) => {
          if (response === 'cancel') {
            this.$message({
              type: 'info',
              message: '取消输入'
            });
          }
        })
      },
      //添加章节信息
      addChapter() {
        chapter.addChapter(this.chapter, this.id)
          .then(() => {
            this.$message({
              type: 'success',
              message: '添加成功'
            });
            this.getChaptersByCourseId(this.id)
          }).catch((response) => {
          this.$message({
            type: 'error',
            message: '添加失败'
          });
        })
      }
      ,
      //查询所有章节小节信息
      getChaptersByCourseId(courseId) {
        chapter.getChaptersByCourseId(courseId)
          .then((response) => {
            this.chaptersList = response.data.items
            console.log(this.chaptersList)
          }).catch(() => {
          this.$message({
            type: 'error',
            message: '查询章节失败!'
          });
        })
      }
      ,
      previous() {
        this.$router.push({path: '/edu/course/addCourse/' + this.id})
      }
      ,
      next() {
        this.$router.push({path: '/edu/course/release/' + this.id})
      }
    }
  }
</script>
<style scoped>
  .chanpterList {
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
  }

  .chanpterList li {
    position: relative;
  }

  .chanpterList p {
    float: left;
    font-size: 20px;
    margin: 10px 0;
    padding: 10px;
    height: 70px;
    line-height: 50px;
    width: 100%;
    border: 1px solid #DDD;
  }

  .chanpterList .acts {
    float: right;
    font-size: 14px;
  }

  .videoList {
    padding-left: 50px;
  }

  .videoList p {
    float: left;
    font-size: 14px;
    margin: 10px 0;
    padding: 10px;
    height: 50px;
    line-height: 30px;
    width: 100%;
    border: 1px dotted #DDD;
  }
</style>
