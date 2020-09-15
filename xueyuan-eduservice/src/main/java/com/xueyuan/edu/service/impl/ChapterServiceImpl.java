package com.xueyuan.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xueyuan.edu.entity.Chapter;
import com.xueyuan.edu.entity.Video;
import com.xueyuan.edu.entity.vo.VideoVo;
import com.xueyuan.edu.entity.vo.addChapter;
import com.xueyuan.edu.entity.vo.chapterVo;
import com.xueyuan.edu.mapper.ChapterMapper;
import com.xueyuan.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyuan.edu.service.VideoService;
import org.apache.velocity.runtime.directive.contrib.For;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-04-01
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
    @Autowired
    private ChapterMapper chapterMapper;
    @Autowired
    private VideoService videoService;

    @Override
    public void removeByCourseId(String id) {
        QueryWrapper<Chapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        chapterMapper.delete(queryWrapper);
    }

    @Override
    public List<chapterVo> getChapterById(String courseId) {
        //根据id查询所有章节
        QueryWrapper<Chapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        List<Chapter> chapters = chapterMapper.selectList(queryWrapper);
        //根据id查询所有小节
        List<Video> videos = videoService.getByCourseId(courseId);
        List<chapterVo> chapterVos = new ArrayList<>();
        for (Chapter chapter : chapters) {
            chapterVo chapterVo = new chapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            List<VideoVo> videoVos = new ArrayList<>();
            for (Video video : videos) {
                if (video.getChapterId().equals(chapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setVideoVos(videoVos);
            chapterVos.add(chapterVo);
        }
        return chapterVos;
    }

    @Override
    public Boolean addChapter(String id, addChapter addChapter) {
        Chapter chapter = new Chapter();
        chapter.setCourseId(id);
        chapter.setTitle(addChapter.getTitle());
        chapter.setSort(addChapter.getSort());
        int result = chapterMapper.insert(chapter);
        return result > 0;
    }

    @Override
    public Boolean removeChapterById(String value) {
        List<Video> videos = videoService.getByChapterId(value);
        if (videos.size() == 0) {
            int result = chapterMapper.deleteById(value);
            return result > 0;
        } else {
            return false;
        }
    }

    @Override
    public Boolean updateChapterById(String id, addChapter addChapter) {
        Chapter chapter = new Chapter();
        chapter.setId(id);
        BeanUtils.copyProperties(addChapter, chapter);
        int result = chapterMapper.updateById(chapter);
        return result > 0;
    }

    @Override
    public List<Chapter> getChapterByCourseId(String id) {
        QueryWrapper<Chapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        List<Chapter> chapters = chapterMapper.selectList(queryWrapper);
        return chapters;
    }
}
