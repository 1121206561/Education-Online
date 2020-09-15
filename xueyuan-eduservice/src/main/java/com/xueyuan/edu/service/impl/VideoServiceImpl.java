package com.xueyuan.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xueyuan.edu.entity.Video;
import com.xueyuan.edu.entity.vo.addVideo;
import com.xueyuan.edu.mapper.VideoMapper;
import com.xueyuan.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyuan.edu.service.VodClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-04-01
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private VodClient vodClient;

    @Override
    public void removeByCourseId(String id) {
        //根据课程删除所有小节的腾讯云上的视频
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        List<Video> videos = videoMapper.selectList(queryWrapper);
        for (Video video : videos) {
            vodClient.remove(video.getVideoSourceId());
        }
        videoMapper.delete(queryWrapper);
    }

    @Override
    public List<Video> getByCourseId(String courseId) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_Id", courseId);
        List<Video> videos = videoMapper.selectList(queryWrapper);
        return videos;
    }

    @Override
    public List<Video> getByChapterId(String value) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", value);
        List<Video> videos = videoMapper.selectList(queryWrapper);
        return videos;
    }

    @Override
    public Boolean addVideoById(String courseId, addVideo addVideo) {
        Video video = new Video();
        BeanUtils.copyProperties(addVideo, video);
        video.setCourseId(courseId);
        int result = videoMapper.insert(video);
        return result > 0;
    }

    @Override
    public Boolean updateVideoById(String videoId, addVideo addvideo) {
        Video video = new Video();
        video.setTitle(addvideo.getTitle());
        video.setSort(addvideo.getSort());
        video.setVideoSourceId(addvideo.getVideoSourceId());
        video.setId(videoId);
        int result = videoMapper.updateById(video);
        return result > 0;
    }

    @Override
    public boolean removeVideoById(String id) {
        //删除小节的同时删除腾讯云上存储的视频
        Video video = videoMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            vodClient.remove(videoSourceId);
        }
        int result = videoMapper.deleteById(id);
        return result > 0;
    }
}