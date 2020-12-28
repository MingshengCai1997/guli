package com.sheng.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sheng.eduservice.entity.EduSubject;
import com.sheng.eduservice.entity.excel.SubjectData;
import com.sheng.eduservice.entity.subject.OneSubject;
import com.sheng.eduservice.entity.subject.TwoSubject;
import com.sheng.eduservice.listener.SubjectExcelListener;
import com.sheng.eduservice.mapper.EduSubjectMapper;
import com.sheng.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.Subject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            // 调用方法进行读取
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 展示所有的课程分类
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        // 1. 获取出一级和二级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        // 可以看一下serviceImpl中的实现类，是注入了一个basemapper的
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        // 查询出所有的二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        // 创建一个集合，存储最终的结合结果
        List<OneSubject> finalSubjectList = new ArrayList<>();
        // 2. 进行封装一级
        // 就是将所有的一级遍历，然后取出所有的值，再封装进去，同样，将所有的二级放到一级中去
        // 将创建的多个oneSubject 放到final集合中去
        for (EduSubject oneEduSubject : oneSubjectList) {
            OneSubject oneSubject = new OneSubject();
            // ------------------方法1 很麻烦---------------------
            /*oneSubject.setId(oneEduSubject.getId());
            oneSubject.setTitle(oneEduSubject.getTitle());*/
            // ------------------方法2 --------------------------
            // 工具类的作用就是将前一个参数的值取出来，放到后面一个参数中去【只会找到对应的属性进行封装】
            BeanUtils.copyProperties(oneEduSubject, oneSubject);

            // 有可能有多个二级分类，所以就是创建一个最终的存放二级的集合
            List<TwoSubject> twoFinalList = new ArrayList<>();

            // 在一级分类的循环中进行二级课程的封装
            for (EduSubject twoEduSubject : twoSubjectList) {
                // 首先判断二级分类的上一级分类是谁
                if(twoEduSubject.getParentId().equals(oneEduSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(twoEduSubject, twoSubject);
                    twoFinalList.add(twoSubject);
                }
            }

            // 将所有的二级分裂的集合放到一级分类的对应位置上去
            oneSubject.setChildren(twoFinalList);

            // 放入最终结果
            finalSubjectList.add(oneSubject);
        }

        return finalSubjectList;
    }
}
