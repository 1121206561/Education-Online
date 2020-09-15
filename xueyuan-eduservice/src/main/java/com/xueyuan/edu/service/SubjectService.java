package com.xueyuan.edu.service;

import com.xueyuan.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xueyuan.edu.entity.vo.subjectVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author YuHaoJun
 * @since 2020-03-27
 */
public interface SubjectService extends IService<Subject> {

    List<String> addSubject(MultipartFile multipartFile);

    List<subjectVo> listAll();

    Boolean removeSubject(String id);

    Boolean appendSubject(String value, String s);
}
