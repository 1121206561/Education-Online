package com.xueyuan.edu.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
//用于封装前台课程详情信息
public class frontCourseVo {
    private String id;
    private String title;
    private String cover;
    private String teacherName;
    private String avatar;
    private String intro;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String description;
    private Integer lessonNum;
    private BigDecimal price;
    private Long buyCount;
    private Long viewCount;
}
