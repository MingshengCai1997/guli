package com.sheng.eduservice.service.impl;

import com.sheng.eduservice.entity.EduCourse;
import com.sheng.eduservice.entity.EduCourseDescription;
import com.sheng.eduservice.entity.vo.CourseInfoVo;
import com.sheng.eduservice.mapper.EduCourseMapper;
import com.sheng.eduservice.service.EduCourseDescriptionService;
import com.sheng.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sheng.servicebase.handler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author cms
 * @since 2020-12-23
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    // 添加课程的基本信息
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 向课程表中添加课程的基本信息
        // 首先进行类型的转换,因为要求是这中类型的
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);// 返回的是影响行数
        if(insert <= 0) {
            throw new GuliException(20001, "添加课程信息失败");
        }
        // 为了保证一对一，要让描述和信息的id相同
        String cid = eduCourse.getId();

        // 向课程简介表中添加简介的信息 edu_course_description
        // 没有办法直接在这里进行添加，所以使用对应的service进行操作
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(cid);
        boolean save = eduCourseDescriptionService.save(courseDescription);
        if(!save){
            throw new GuliException(20001, "课程详情信息保存失败");
        }
        return cid;
    }
}
