package com.sheng.eduservice.controller;


import com.sheng.eduservice.entity.EduTeacher;
import com.sheng.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 * @ControllerAdvice(basePackages = "com.sheng.eduservice.controller")
 * @author cms
 * @since 2020-11-30
 */
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    // 1. 查询将讲师表中的所有数据
    @GetMapping("findAll")
    public List<EduTeacher> findAllTeacher() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return list;
    }

    // 2. 对导师进行逻辑删除
    @DeleteMapping("{id}")
    public boolean removeById(@PathVariable String id) {
        return eduTeacherService.removeById(id);
    }
}

