package com.xueyuan.edu.controller;


import com.xueyuan.edu.R;
import com.xueyuan.edu.entity.vo.addChapter;
import com.xueyuan.edu.entity.vo.chapterVo;
import com.xueyuan.edu.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-04-01
 */
@RestController
@RequestMapping("/edu/chapter")
@CrossOrigin
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    //根据课程id查询所有章节小节信息
    @PostMapping("/getChapterById/{courseId}")
    public R getChapterById(@PathVariable("courseId") String courseId) {
        List<chapterVo> chapterVos = chapterService.getChapterById(courseId);
        return R.ok().data("items", chapterVos);
    }

    @PostMapping("/addChapter/{id}")
    public R addChapter(@PathVariable("id") String id, @RequestBody addChapter addChapter) {
        Boolean b = chapterService.addChapter(id, addChapter);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @PostMapping("/removeChapterById/{value}")
    public R removeChapterById(@PathVariable String value) {
        Boolean b = chapterService.removeChapterById(value);
        if (b) {
            return R.ok();
        } else {
            return R.error().message("请先删除该章节下的小节");
        }
    }

    @PostMapping("/updateChapterById/{id}")
    public R updateChapterById(@PathVariable("id") String id, @RequestBody addChapter addChapter) {
        Boolean b = chapterService.updateChapterById(id, addChapter);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

