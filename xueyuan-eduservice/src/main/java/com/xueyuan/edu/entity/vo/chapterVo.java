package com.xueyuan.edu.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class chapterVo {
    private String id;
    private String title;
    private Integer sort;
    private List<VideoVo> videoVos;
}
