package com.xueyuan.edu.service;

import com.xueyuan.edu.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("xueyuan-vid")  //调用注册中心中的其他端口的Controller
public interface VodClient {

    @PostMapping("/edu/vid/remove/{videoSourceId}")
    R remove(@PathVariable("videoSourceId") String videoSourceId);

}