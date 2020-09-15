package com.xueyuan.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyuan.edu.R;
import com.xueyuan.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyuan.edu.entity.dto.CourseAllTermDTO;
import com.xueyuan.edu.entity.dto.CourseTermDTO;
import com.xueyuan.edu.entity.dto.addCourseDTO;
import com.xueyuan.edu.entity.vo.frontChapterVo;
import com.xueyuan.edu.entity.vo.frontCourseVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-03-30
 */
public interface CourseService extends IService<Course> {

    R addCourse(addCourseDTO addCourseDTO);

    R getCourseById(String id);

    Boolean updateCourseById(addCourseDTO addCourseDTO);

    List<Course> getAllCourse(Page<Course> coursePage, CourseTermDTO courseTermDTO);

    Boolean removeCourse(String id);

    CourseAllTermDTO getAllCourseByAll(String courseId);

    Boolean publishCourse(String id);

    Map<String, Object> getPageCourse(Long page, Long limit);

    frontCourseVo getCourseAll(String id);

    List<frontChapterVo> getChapterVideo(String id);
}
