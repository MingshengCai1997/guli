package com.sheng.eduservice.controller;


import com.sheng.commonutils.R;
import com.sheng.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


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
}

