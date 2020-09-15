package com.xueyuan.edu.service.impl;

import com.xueyuan.edu.entity.CourseDescription;
import com.xueyuan.edu.mapper.CourseDescriptionMapper;
import com.xueyuan.edu.service.CourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-03-30
 */
@Service
public class CourseDescriptionServiceImpl extends ServiceImpl<CourseDescriptionMapper, CourseDescription> implements CourseDescriptionService {

}
