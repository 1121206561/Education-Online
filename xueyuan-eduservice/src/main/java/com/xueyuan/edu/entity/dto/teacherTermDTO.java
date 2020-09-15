package com.xueyuan.edu.entity.dto;

import lombok.Data;

//创建用于接收参数的实体类
@Data
public class teacherTermDTO {
    private String name;
    private String level;
    private String beginTime;
    private String endTime;
}
