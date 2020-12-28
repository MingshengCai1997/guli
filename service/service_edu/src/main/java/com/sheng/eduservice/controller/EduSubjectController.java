package com.sheng.eduservice.controller;


import com.sheng.commonutils.R;
import com.sheng.eduservice.entity.subject.OneSubject;
import com.sheng.eduservice.service.EduSubjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author cms
 * @since 2020-12-19
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

    // 添加课程的分类
    // 获取到上传的文件，然后将文件的内容读取

    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        // 上传过来的excel文件
        subjectService.saveSubject(file, subjectService);
        return R.ok();
    }

    // 课程分类树形列表
    @ApiOperation(value = "嵌套数据列表")
    @GetMapping("getAllSubject")
    public R nestedList(){
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list", list);
    }
}

