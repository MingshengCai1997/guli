package com.sheng.eduservice.service.impl;

import com.sheng.eduservice.entity.EduCourse;
import com.sheng.eduservice.entity.EduCourseDescription;
import com.sheng.eduservice.entity.EduVideo;
import com.sheng.eduservice.entity.vo.CourseInfoVo;
import com.sheng.eduservice.entity.vo.CoursePublishVo;
import com.sheng.eduservice.mapper.EduCourseMapper;
import com.sheng.eduservice.service.EduChapterService;
import com.sheng.eduservice.service.EduCourseDescriptionService;
import com.sheng.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sheng.eduservice.service.EduVideoService;
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
    // 注入小结和章节
    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;


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

    // 根据课程id查询对应的课程信息【课程的基本信息包含两张表的数据，一个是课程表，一个是数据表】
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        // 1. 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        // 3. 封装到相应的对象中去
        // 【使用相应的工具类，取出来在放进去】
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        // 2. 查询课程的简介表
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);

        // 接下来将描述信息放进去
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    // 修改课程信息【当然还是操作的是两张表】
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        // 1. 修改课程表
        // 这基本的mapper中的参数放的是Educourse对象，但是现在的参数是courseinfo对象，所以先进行转换
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int i = baseMapper.updateById(eduCourse);
        // 进行判断
        if(i == 0) {
            throw new GuliException(20001, "修改课程信息失败");
        }
        // 修改描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishInfo = baseMapper.getPublishInfo(id);
        return publishInfo;
    }

    // 删除课程
    @Override
    public void removeCourse(String courseId) {
        // 1. 根据课程id删除课程的小结
        eduVideoService.removeVideoByCourseId(courseId);
        // 2. 根据课程id删除课程的章节
        eduChapterService.removeChapterById(courseId);
        // 3. 删除课程id删除对应的描述
        eduCourseDescriptionService.removeById(courseId);
        // 4. 删除课程
        int result = baseMapper.deleteById(courseId);

        if (result == 0) {
            throw new GuliException(20001, "删除失败");
        }
    }
}
