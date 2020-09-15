package com.xueyuan.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xueyuan.edu.R;
import com.xueyuan.edu.entity.Teacher;
import com.xueyuan.edu.entity.dto.teacherTermDTO;
import com.xueyuan.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-03-22
 */
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin  //解决跨域问题  在访问时只要协议 端口号 ip地址有一个不一样就会产生跨域问题
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("login")
    public R getLogin() {
        return R.ok().data("token", "admin");
    }

    @GetMapping("info")
    public R getInfo() {
        return R.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    @GetMapping("getAll")
    public R getAll() {
        List<Teacher> teacherList = teacherService.list(null);
        return R.ok().data("items", teacherList);
    }

    @DeleteMapping("/deleteTeacher/{id}")
    public R deleteById(@PathVariable("id") String id) {
        Boolean b = teacherService.removeById(id);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //分页查询数据
    @GetMapping("getPageAll/{page}/{limit}")
    public R getPageAll(@PathVariable("page") Long page, @PathVariable("limit") Long limit) {
        Page<Teacher> teacherPage = new Page<>(page, limit);
        //分页查询
        teacherService.page(teacherPage, null);
        //总数据个数
        Long total = teacherPage.getTotal();
        //查询出来的数据
        List<Teacher> teacherList = teacherPage.getRecords();
        return R.ok().data("total", total).data("items", teacherList);
    }

    //多条件分页查询
    @PostMapping("getPageTermAll/{page}/{limit}")
    public R pageTermAll(@PathVariable("page") Long page, @PathVariable("limit") Long limit, @RequestBody(required = false) teacherTermDTO teacherTermDTO) {
        Page<Teacher> teacherPage = new Page<>(page, limit);
        //在service层进行条件查询
        teacherService.pageTermAll(teacherPage, teacherTermDTO);
        Long total = teacherPage.getTotal();
        //查询出来的数据
        List<Teacher> teacherList = teacherPage.getRecords();
        return R.ok().data("total", total).data("items", teacherList);
    }

    //根据id查询
    @GetMapping("getAllById/{id}")
    public R getAllById(@PathVariable("id") String id) {
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    //添加数据
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody Teacher teacher) {
        Boolean b = teacherService.save(teacher);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //更新数据
    @PostMapping("updateTeacher/{id}")
    public R updateTeacher(@PathVariable("id") String id, @RequestBody Teacher teacher) {
        Boolean b = teacherService.updateById(teacher);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}
       

