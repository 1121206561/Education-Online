package com.xueyuan.edu.service;

import com.xueyuan.edu.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-04-07
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    Boolean addStatisticsDaily(String day);

    Map<String, Object> getStatisticsCount(String type, String begin, String end);
}
