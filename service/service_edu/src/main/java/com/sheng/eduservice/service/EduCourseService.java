package com.sheng.eduservice.service;

import com.sheng.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sheng.eduservice.entity.vo.CourseInfoVo;
import com.sheng.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author cms
 * @since 2020-12-23
 */
public interface EduCourseService extends IService<EduCourse> {

    // 添加课程的基本信息
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    // 根据课程id查询基本信息
    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);

    void removeCourse(String courseId);
}
