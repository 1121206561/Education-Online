package com.xueyuan.eduvid.controller;

import com.xueyuan.edu.R;
import com.xueyuan.eduvid.service.VidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/edu/vid")
@CrossOrigin
public class VidController {
    @Autowired
    private VidService vidService;

    @PostMapping("/sign")
    public R getSign() {
        String signature = vidService.getSignature();
        if (signature == null) {
            return R.error().message("获取失败");
        } else {
            return R.ok().data("item", signature);
        }
    }

    @PostMapping("/remove/{videoSourceId}")
    public R remove(@PathVariable("videoSourceId") String videoSourceId) {
        Boolean b = vidService.remove(videoSourceId);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}