package com.xueyuan.edu.mapper;

import com.xueyuan.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xueyuan.edu.entity.dto.CourseAllTermDTO;
import com.xueyuan.edu.entity.vo.frontCourseVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-03-30
 */
public interface CourseMapper extends BaseMapper<Course> {

    CourseAllTermDTO getAllCourseByAll(String courseId);

    frontCourseVo getCourseAll(String id);
}
