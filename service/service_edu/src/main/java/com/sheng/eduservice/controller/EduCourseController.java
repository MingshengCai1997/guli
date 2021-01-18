package com.sheng.eduservice.controller;


import com.sheng.commonutils.R;
import com.sheng.eduservice.entity.EduCourse;
import com.sheng.eduservice.entity.vo.CourseInfoVo;
import com.sheng.eduservice.entity.vo.CoursePublishVo;
import com.sheng.eduservice.service.EduCourseService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author cms
 * @since 2020-12-23
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    // 添加课程基本信息的方法
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        // 返回之前添加的课程的id，为了后面添加课程大纲使用

        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    // 根据课程id查询课程的基本信息
    @GetMapping("getCourseInfoById/{courseId}")
    public R getCourseInfoById(@PathVariable String courseId) {
        // 我们数据回显的时候，返回的是CourseInfo那个对象
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    // 修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    // 根据id查询课程信息确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = eduCourseService.publishCourseInfo(id);
        return R.ok().data("publishCourse", coursePublishVo);
    }

    // 修改状态为发布
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");  // 设置课程的发布状态
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    // TODO 完善待条件查询和课程分页
    @GetMapping
    public R getCourseList() {
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("list", list);
    }

    // 删除课程【注意：需要删除对应的章节和小结】
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        eduCourseService.removeCourse(courseId);
        return R.ok();
    }
}

