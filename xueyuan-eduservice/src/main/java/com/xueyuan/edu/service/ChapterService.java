package com.xueyuan.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyuan.edu.entity.Chapter;
import com.xueyuan.edu.entity.vo.addChapter;
import com.xueyuan.edu.entity.vo.chapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-04-01
 */
public interface ChapterService extends IService<Chapter> {

    void removeByCourseId(String id);

    List<chapterVo> getChapterById(String courseId);

    Boolean addChapter(String id, addChapter addChapter);

    Boolean removeChapterById(String value);

    Boolean updateChapterById(String id, addChapter addChapter);

    List<Chapter> getChapterByCourseId(String id);
}
