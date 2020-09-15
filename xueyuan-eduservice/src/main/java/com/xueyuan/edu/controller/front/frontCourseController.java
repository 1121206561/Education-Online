package com.xueyuan.edu.controller.front;

import com.xueyuan.edu.R;
import com.xueyuan.edu.entity.vo.frontChapterVo;
import com.xueyuan.edu.entity.vo.frontCourseVo;
import com.xueyuan.edu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/edu/frontCourse")
@CrossOrigin
public class frontCourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/getPageCourse/{page}/{limit}")
    public R getPageCourse(@PathVariable("page") Long page, @PathVariable("limit") Long limit) {
        Map<String, Object> map = courseService.getPageCourse(page, limit);
        return R.ok().data("items", map);
    }

    //课程详情栏的显示
    @PostMapping("/getCourseAll/{id}")
    public R getCourseAll(@PathVariable("id") String id) {
        //查询课程所有信息
        frontCourseVo frontCourseVo = courseService.getCourseAll(id);
        //查询章节和小节所有信息
        List<frontChapterVo> frontChapterVos = courseService.getChapterVideo(id);
        return R.ok().data("items", frontCourseVo).data("chapters", frontChapterVos);
    }
}
