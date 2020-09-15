package com.xueyuan.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyuan.edu.entity.Course;
import com.xueyuan.edu.entity.Teacher;
import com.xueyuan.edu.entity.dto.teacherTermDTO;
import com.xueyuan.edu.mapper.CourseMapper;
import com.xueyuan.edu.mapper.TeacherMapper;
import com.xueyuan.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-03-22
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public void pageTermAll(Page<Teacher> teacherPage, teacherTermDTO teacherTermDTO) {
        //如果查询条件为空,则不带条件查询
        if (teacherTermDTO == null) {
            teacherMapper.selectPage(teacherPage, null);
            return;
        }

        //对其他条件进行判断,如果有则进行拼接
        String name = teacherTermDTO.getName();
        String level = teacherTermDTO.getLevel();
        String begin = teacherTermDTO.getBeginTime();
        String end = teacherTermDTO.getEndTime();
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }
        teacherMapper.selectPage(teacherPage, queryWrapper);
    }

    @Override
    //前台讲师列表页的显示
    public Map<String, Object> getPageTeacher(Long page, Long limit) {
        Page<Teacher> teacherPage = new Page<>(page, limit);
        teacherMapper.selectPage(teacherPage, null);
        Map<String, Object> map = new HashMap<>();
        map.put("teachers", teacherPage.getRecords());
        map.put("current", teacherPage.getCurrent());
        map.put("pages", teacherPage.getPages());
        map.put("size", teacherPage.getSize());
        map.put("total", teacherPage.getTotal());
        map.put("hasNext", teacherPage.hasNext());
        map.put("hasPrevious", teacherPage.hasPrevious());
        return map;
    }

    @Override
    public List<Course> getCourseById(String id) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", id);
        List<Course> courseList = courseMapper.selectList(queryWrapper);
        return courseList;
    }
}
