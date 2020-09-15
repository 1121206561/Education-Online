package com.xueyuan.edu.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class frontChapterVo {
    private String id;
    private String title;
    private List<frontVideoVo> frontVideoVos;
}
