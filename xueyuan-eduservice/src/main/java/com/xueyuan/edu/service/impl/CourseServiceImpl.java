package com.xueyuan.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xueyuan.edu.R;
import com.xueyuan.edu.entity.Chapter;
import com.xueyuan.edu.entity.Course;
import com.xueyuan.edu.entity.CourseDescription;
import com.xueyuan.edu.entity.Video;
import com.xueyuan.edu.entity.dto.CourseAllTermDTO;
import com.xueyuan.edu.entity.dto.CourseTermDTO;
import com.xueyuan.edu.entity.dto.addCourseDTO;
import com.xueyuan.edu.entity.vo.frontChapterVo;
import com.xueyuan.edu.entity.vo.frontCourseVo;
import com.xueyuan.edu.entity.vo.frontVideoVo;
import com.xueyuan.edu.handler.MyException;
import com.xueyuan.edu.mapper.CourseMapper;
import com.xueyuan.edu.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-03-30
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseDescriptionService courseDescriptionService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private VideoService videoService;

    @Override
    @Transactional //开启事务 只要一个出错就会滚
    public R addCourse(addCourseDTO addCourseDTO) {
        //把得到的数据装换成数据库需要存储的对象
        Course course = new Course();
        BeanUtils.copyProperties(addCourseDTO, course);
        int result = courseMapper.insert(course);
        //如果添加课程报错则抛出异常信息不执行后面的步骤
        if (result == 0) {
            throw new MyException(20001, "添加课程信息报错");
        }
        //课程描述添加到课程描述数据库中
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(course.getId());
        courseDescription.setDescription(addCourseDTO.getDescription());
        boolean b = courseDescriptionService.save(courseDescription);
        if (b) {
            return R.ok().data("id", course.getId());
        } else {
            return R.error().message("添加课程失败");
        }
    }

    @Override
    public R getCourseById(String id) {
        //上一步操作 根据id把描述和信息传递回前台显示出来
        Course course = courseMapper.selectById(id);
        if (course == null) {
            throw new MyException(20001, "请求的信息不存在");
        }
        String parentId = subjectService.getById(course.getSubjectId()).getParentId();
        addCourseDTO addCourseDTO = new addCourseDTO();
        BeanUtils.copyProperties(course, addCourseDTO);
        CourseDescription courseDescription = courseDescriptionService.getById(id);
        if (courseDescription != null) {
            BeanUtils.copyProperties(courseDescription, addCourseDTO);
        }
        //传递分类的一级分类用于回显分类
        return R.ok().data("items", addCourseDTO).data("parentId", parentId);
    }

    @Override
    public Boolean updateCourseById(addCourseDTO addCourseDTO) {
        //根据id更新操作
        Course course = new Course();
        BeanUtils.copyProperties(addCourseDTO, course);
        int result = courseMapper.updateById(course);
        if (result == 0) {
            throw new MyException(20001, "更新信息出错乐");
        }
        CourseDescription courseDescription = new CourseDescription();
        BeanUtils.copyProperties(addCourseDTO, courseDescription);
        boolean b = courseDescriptionService.updateById(courseDescription);
        return b;
    }

    @Override
    public List<Course> getAllCourse(Page<Course> coursePage, CourseTermDTO courseTermDTO) {
        if (courseTermDTO == null) {
            courseMapper.selectPage(coursePage, null);
        } else {
            QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(courseTermDTO.getTitle())) {
                queryWrapper.like("title", courseTermDTO.getTitle());
            }
            if (!StringUtils.isEmpty(courseTermDTO.getSubjectParentId())) {
                queryWrapper.isNotNull("subject_id");
            }
            if (!StringUtils.isEmpty(courseTermDTO.getTeacherId())) {
                queryWrapper.eq("teacher_id", courseTermDTO.getTeacherId());
            }
            if (!StringUtils.isEmpty(courseTermDTO.getSubjectId())) {
                queryWrapper.eq("subject_id", courseTermDTO.getSubjectId());
            }
            courseMapper.selectPage(coursePage, queryWrapper);
        }
        return coursePage.getRecords();
    }

    //删除课程级课程关联的章节描述
    @Override
    public Boolean removeCourse(String id) {
        Integer i = courseMapper.deleteById(id);
        if (i == 0) {
            throw new MyException(20001, "该课程不存在");
        } else {
            courseDescriptionService.removeById(id);
            chapterService.removeByCourseId(id);
            videoService.removeByCourseId(id);
            return i > 0;
        }
    }

    @Override
    public CourseAllTermDTO getAllCourseByAll(String courseId) {
        CourseAllTermDTO allCourseByAll = courseMapper.getAllCourseByAll(courseId);
        return allCourseByAll;
    }

    @Override
    public Boolean publishCourse(String id) {
        int result = courseMapper.updateById(courseMapper.selectById(id).setStatus("Normal"));
        return result > 0;
    }

    @Override
    //查询前台课程页面分页信息
    public Map<String, Object> getPageCourse(Long page, Long limit) {
        Page<Course> coursePage = new Page<>(page, limit);
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "Normal");
        courseMapper.selectPage(coursePage, queryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("items", coursePage.getRecords());
        map.put("current", coursePage.getCurrent());
        map.put("pages", coursePage.getPages());
        map.put("size", coursePage.getSize());
        map.put("total", coursePage.getTotal());
        map.put("hasNext", coursePage.hasNext());
        map.put("hasPrevious", coursePage.hasPrevious());
        return map;
    }

    @Override
    //查询课程详细信息
    public frontCourseVo getCourseAll(String id) {
        frontCourseVo frontCourseVo = courseMapper.getCourseAll(id);
        return frontCourseVo;
    }

    @Override
    //查询章节小节信息
    public List<frontChapterVo> getChapterVideo(String id) {
        List<Chapter> chapters = chapterService.getChapterByCourseId(id);
        List<frontChapterVo> frontChapterVos = new ArrayList<>();
        for (Chapter chapter : chapters) {
            frontChapterVo frontChapterVo = new frontChapterVo();
            BeanUtils.copyProperties(chapter, frontChapterVo);
            List<Video> videos = videoService.getByChapterId(chapter.getId());
            List<frontVideoVo> frontVideoVos = new ArrayList<>();
            for (Video video : videos) {
                frontVideoVo frontvideVo = new frontVideoVo();
                BeanUtils.copyProperties(video, frontvideVo);
                frontVideoVos.add(frontvideVo);
            }
            frontChapterVo.setFrontVideoVos(frontVideoVos);
            frontChapterVos.add(frontChapterVo);
        }
        return frontChapterVos;
    }
}
