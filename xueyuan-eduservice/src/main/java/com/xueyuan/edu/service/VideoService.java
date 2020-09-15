package com.xueyuan.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyuan.edu.entity.Video;
import com.xueyuan.edu.entity.vo.addVideo;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-04-01
 */
public interface VideoService extends IService<Video> {

    void removeByCourseId(String id);

    List<Video> getByCourseId(String courseId);

    List<Video> getByChapterId(String value);

    Boolean addVideoById(String courseId, addVideo addvideo);

    Boolean updateVideoById(String videoId, addVideo addvideo);

    boolean removeVideoById(String id);
}
