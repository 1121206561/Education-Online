package com.xueyuan.edu.controller;


import com.xueyuan.edu.R;
import com.xueyuan.edu.entity.Subject;
import com.xueyuan.edu.entity.vo.subjectVo;
import com.xueyuan.edu.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-03-27
 */
@RestController
@RequestMapping("/edu/subject")
@CrossOrigin
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping("addSubject")
    public R addSubject(MultipartFile multipartFile) {
        //上传一个xlms的file文件 将文件的内容存入到数据库中
        List<String> subjectList = subjectService.addSubject(multipartFile);
        if (subjectList.size() == 0) {
            return R.ok();
        } else {
            return R.error().data("items", subjectList);
        }
    }

    @PostMapping("list")
    public R list() {
        List<subjectVo> subjectVoList = subjectService.listAll();
        if (subjectVoList.size() == 0) {
            return R.error();
        } else {
            return R.ok().data("items", subjectVoList);
        }
    }

    @DeleteMapping("/removeSubject/{id}")
    public R removeSubject(@PathVariable("id") String id) {
        Boolean b = subjectService.removeSubject(id);
        if (b) {
            return R.ok();
        } else {
            return R.error().message("不能直接删除一级分类");
        }
    }

    @GetMapping("/appendSubject/{value}")
    public R appendSubject(@PathVariable("value") String value) {
        Boolean b = subjectService.appendSubject(value, "0");
        if (b) {
            return R.ok();
        } else {
            return R.error().message("添加失败");
        }
    }

    @GetMapping("/appendTwoSubject/{value}/{id}")
    public R appendTwoSubject(@PathVariable("value") String value, @PathVariable("id") String id) {
        Boolean b = subjectService.appendSubject(value, id);
        if (b) {
            return R.ok();
        } else {
            return R.error().message("添加失败");
        }
    }
}

