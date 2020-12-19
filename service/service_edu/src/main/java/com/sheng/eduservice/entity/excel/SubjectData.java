package com.sheng.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;

public class SubjectData {
    @ExcelProperty(index = 0)
    private int oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
