package com.xueyuan.edu.controller;


import com.xueyuan.edu.R;
import com.xueyuan.edu.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-04-07
 */
@RestController
@RequestMapping("/edu/statistics-daily")
@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @PostMapping("/addStatisticsDaily/{day}")
    public R addStatisticsDaily(@PathVariable("day") String day) {
        Boolean b = statisticsDailyService.addStatisticsDaily(day);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }

    }

    @PostMapping("/getStatisticsCount/{type}/{begin}/{end}")
    public R getStatisticsCount(@PathVariable String type, @PathVariable String begin, @PathVariable String end) {
        Map<String, Object> map = statisticsDailyService.getStatisticsCount(type, begin, end);
        return R.ok().data("items", map);
    }
}

