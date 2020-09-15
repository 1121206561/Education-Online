package com.xueyuan.edu.controller;


import com.xueyuan.edu.R;
import com.xueyuan.edu.entity.vo.addVideo;
import com.xueyuan.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-04-01
 */
@RestController
@RequestMapping("/edu/video")
@CrossOrigin
public class VideoController {
    @Autowired
    private VideoService videoService;

    @PostMapping("/addVideoById/{courseId}")
    public R addVideoById(@PathVariable("courseId") String courseId, @RequestBody addVideo addvideo) {
        Boolean b = videoService.addVideoById(courseId, addvideo);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @PostMapping("/updateVideoById/{videoId}")
    public R updateVideoById(@PathVariable("videoId") String videoId, @RequestBody addVideo addvideo) {
        Boolean b = videoService.updateVideoById(videoId, addvideo);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @PostMapping("/removeVideoById/{id}")
    public R removeVideoById(@PathVariable("id") String id) {
        boolean b = videoService.removeVideoById(id);
        if (b) {
            return R.ok();
        } else {
            return R.error().message("删除失败");
        }
    }
}

