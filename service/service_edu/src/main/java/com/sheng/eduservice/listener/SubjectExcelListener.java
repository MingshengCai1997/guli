package com.sheng.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sheng.eduservice.entity.EduSubject;
import com.sheng.eduservice.entity.excel.SubjectData;
import com.sheng.eduservice.service.EduSubjectService;
import com.sheng.servicebase.handler.GuliException;

import javax.management.Query;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public EduSubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }


    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null) {
            throw new GuliException(20001, "数据为空");
        }
        // 一行一行的读取，第一个值是一级分类，第二个值是二级分类
        // 先判断一级是否重复
        EduSubject existOne = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if(existOne == null) {
            // 如果是空的话，就是没有重复的一级分类可以进行添加
            existOne = new EduSubject();
            existOne.setParentId("0");
            existOne.setTitle(subjectData.getOneSubjectName());  // 一级分类名称
            subjectService.save(existOne);
        }

        // 获取一级分类的id值
        String pid = existOne.getId();
        // 再判断二级是否重复
        EduSubject existTwo = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if(existTwo == null) {
            // 如果是空的话，就是没有重复的一级分类可以进行添加
            existTwo = new EduSubject();
            existTwo.setParentId(pid);
            existTwo.setTitle(subjectData.getTwoSubjectName());  // 一级分类名称
            subjectService.save(existTwo);
        }
    }

    // 判断一级分类不可以重复添加【为了可以查询数据库，将eduservice传进来】
    private EduSubject existOneSubject(EduSubjectService subjectService, String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id","0");
        EduSubject one = subjectService.getOne(wrapper);
        return one;
    }

    // 二级分类不可以重复添加
    private EduSubject existTwoSubject(EduSubjectService subjectService, String name, String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id",pid);
        EduSubject two = subjectService.getOne(wrapper);
        return two;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
