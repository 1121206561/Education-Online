package com.xueyuan.edu.service;

import com.xueyuan.edu.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("xueyuan-ucenter")
public interface UcenterClient {

    @PostMapping("/edu/ucenter-member/getNumberByDay/{day}")
    R getNumberByDay(@PathVariable("day") String day);
}
