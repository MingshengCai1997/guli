package com.sheng.eduservice.entity.subject;

import lombok.Data;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

@Data
public class OneSubject {
    private String id;
    private String title;

    // 一个一级中可以有多个二级分类
    private List<TwoSubject> children = new ArrayList<>();
}
