package com.xueyuan.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyuan.edu.entity.Course;
import com.xueyuan.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyuan.edu.entity.dto.teacherTermDTO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-03-22
 */
public interface TeacherService extends IService<Teacher> {

    void pageTermAll(Page<Teacher> teacherPage, teacherTermDTO teacherTermDTO);

    Map<String, Object> getPageTeacher(Long page, Long limit);

    List<Course> getCourseById(String id);
}
