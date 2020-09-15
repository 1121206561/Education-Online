package com.xueyuan.edu.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class subjectVo {
    private String id;
    private String title;
    private List<subjectTwoVo> subjectTwoVoList;
}
