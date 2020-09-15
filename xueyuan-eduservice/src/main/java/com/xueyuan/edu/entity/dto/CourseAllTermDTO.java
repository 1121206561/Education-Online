package com.xueyuan.edu.entity.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseAllTermDTO {
    private String id;
    private String title;
    private String cover;
    private String description;
    private Integer lessonNum;
    private BigDecimal price;
    private String teacherName;
    private String subjectLevelOne;
    private String subjectLevelTwo;
}
