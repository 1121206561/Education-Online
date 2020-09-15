package com.xueyuan.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xueyuan.edu.R;
import com.xueyuan.edu.entity.StatisticsDaily;
import com.xueyuan.edu.mapper.StatisticsDailyMapper;
import com.xueyuan.edu.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyuan.edu.service.UcenterClient;
import org.apache.commons.lang3.RandomUtils;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-04-07
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private StatisticsDailyMapper statisticsDailyMapper;
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public Boolean addStatisticsDaily(String day) {
        //由于每天的数据是在实时更新的，所有对每天的数据统计要根据时间进行，每次进行插入数据都要先删除掉今天已经保存的数据
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated", day);
        statisticsDailyMapper.delete(queryWrapper);

        R r = ucenterClient.getNumberByDay(day);
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setDateCalculated(day);
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100, 200));
        statisticsDaily.setRegisterNum((Integer) r.getData().get("item"));
        statisticsDailyMapper.insert(statisticsDaily);
        return true;
    }

    @Override
    //查询统计表中的信息
    public Map<String, Object> getStatisticsCount(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper();
        queryWrapper.between("date_calculated", begin, end).select("date_calculated", type);
        List<StatisticsDaily> statisticsDailyList = statisticsDailyMapper.selectList(queryWrapper);
        List<String> timeList = new ArrayList<>();
        List<Integer> dataList = new ArrayList<>();
        //把查到的list数据封装到一个map集合中去
        for (StatisticsDaily statisticsDaily : statisticsDailyList) {
            timeList.add(statisticsDaily.getDateCalculated());
            switch (type) {
                case "login_num":
                    dataList.add(statisticsDaily.getLoginNum());
                    break;
                case "register_num":
                    dataList.add(statisticsDaily.getRegisterNum());
                    break;
                case "video_view_num":
                    dataList.add(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(statisticsDaily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("time", timeList);
        map.put("data", dataList);
        return map;
    }
}
