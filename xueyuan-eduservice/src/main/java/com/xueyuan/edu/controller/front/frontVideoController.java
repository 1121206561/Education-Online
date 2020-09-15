package com.xueyuan.edu.controller.front;

import com.xueyuan.edu.R;
import com.xueyuan.edu.entity.Video;
import com.xueyuan.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/edu/frontVideo")
@CrossOrigin
public class frontVideoController {
    @Autowired
    private VideoService videoService;

    @PostMapping("/getVideoId/{id}")
    public R getVideoId(@PathVariable("id") String id) {
        Video video = videoService.getById(id);
        return R.ok().data("item", video.getVideoSourceId());
    }
}
