package com.sheng.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SubjectData {
    @ExcelProperty(index = 0)    // 这个注解就是代表第一列和第二列
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
