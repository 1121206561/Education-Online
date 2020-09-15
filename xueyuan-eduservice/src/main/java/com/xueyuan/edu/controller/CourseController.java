package com.xueyuan.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyuan.edu.R;
import com.xueyuan.edu.entity.Course;
import com.xueyuan.edu.entity.dto.CourseAllTermDTO;
import com.xueyuan.edu.entity.dto.CourseTermDTO;
import com.xueyuan.edu.entity.dto.addCourseDTO;
import com.xueyuan.edu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-03-30
 */
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("addCourse")
    public R addCourse(@RequestBody addCourseDTO addCourseDTO) {
        return courseService.addCourse(addCourseDTO);
    }

    @PostMapping("/getCourseById/{id}")
    public R getCourseById(@PathVariable("id") String id) {
        return courseService.getCourseById(id);
    }

    @PostMapping("/updateCourseById/{id}")
    public R updateCourseById(@PathVariable("id") String id, @RequestBody addCourseDTO addCourseDTO) {
        Boolean b = courseService.updateCourseById(addCourseDTO);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @PostMapping("/getAllCourse/{page}/{limit}")
    public R getAllCourse(@PathVariable("page") Long page, @PathVariable("limit") Long limit, @RequestBody(required = false) CourseTermDTO courseTermDTO) {
        Page<Course> coursePage = new Page<>(page, limit);
        List<Course> courseList = courseService.getAllCourse(coursePage, courseTermDTO);
        return R.ok().data("total", coursePage.getTotal()).data("items", courseList);
    }

    @PostMapping("/removeCourse/{id}")
    public R removeCourse(@PathVariable("id") String id) {
        Boolean b = courseService.removeCourse(id);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //查询和课程有关的所有信息多表查询
    @PostMapping("/getAllCourseByAll/{courseId}")
    public R getAllCourseByAll(@PathVariable("courseId") String courseId) {
        CourseAllTermDTO courseAllTermDTO = courseService.getAllCourseByAll(courseId);
        if (courseAllTermDTO != null) {
            return R.ok().data("items", courseAllTermDTO);
        } else {
            return R.error().message("该课程不存在");
        }
    }

    //发布课程
    @PostMapping("/publishCourse/{id}")
    public R publishCourse(@PathVariable("id") String id) {
        Boolean b = courseService.publishCourse(id);
        if (b) {
            return R.ok();
        } else {
            return R.error().message("发布课程失败");
        }
    }
}

