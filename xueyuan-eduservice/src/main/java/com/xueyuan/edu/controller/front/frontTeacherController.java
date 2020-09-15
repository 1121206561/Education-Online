package com.xueyuan.edu.controller.front;

import com.xueyuan.edu.R;
import com.xueyuan.edu.entity.Course;
import com.xueyuan.edu.entity.Teacher;
import com.xueyuan.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/edu/frontTeacher")
@RestController
@CrossOrigin
public class frontTeacherController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/getPageTeacher/{page}/{limit}")
    public R getPageTeacher(@PathVariable("page") Long page, @PathVariable("limit") Long limit) {
        Map<String, Object> map = teacherService.getPageTeacher(page, limit);
        return R.ok().data("items", map);
    }

    @PostMapping("/getTeacherById/{id}")
    public R getTeacherById(@PathVariable("id") String id) {
        //查询前台讲师详情
        Teacher teacher = teacherService.getById(id);
        //查询讲师所讲课程
        List<Course> courseList = teacherService.getCourseById(id);
        return R.ok().data("items", courseList).data("teacher", teacher);
    }
}
