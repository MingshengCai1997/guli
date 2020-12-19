package com.sheng.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.sheng.eduservice.entity.EduSubject;
import com.sheng.eduservice.entity.excel.SubjectData;
import com.sheng.eduservice.mapper.EduSubjectMapper;
import com.sheng.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author cms
 * @since 2020-12-19
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    // 添加课程分类
    @Override
    public void saveSubject(MultipartFile file) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            // 调用方法进行读取
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener()).sheet().doRead();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
