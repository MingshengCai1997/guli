package com.sheng.eduservice.service;

import com.sheng.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sheng.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author cms
 * @since 2020-12-19
 */
public interface EduSubjectService extends IService<EduSubject> {
    // 添加课程分类
    public void saveSubject(MultipartFile file, EduSubjectService subjectService);
    // 课程分类列表
    List<OneSubject> getAllOneTwoSubject();
}
