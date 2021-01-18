package com.sheng.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sheng.eduservice.entity.EduChapter;
import com.sheng.eduservice.entity.EduVideo;
import com.sheng.eduservice.entity.chapter.ChapterVo;
import com.sheng.eduservice.entity.chapter.VideoVo;
import com.sheng.eduservice.mapper.EduChapterMapper;
import com.sheng.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sheng.eduservice.service.EduVideoService;
import com.sheng.servicebase.handler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author cms
 * @since 2020-12-23
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;
    // 课程大纲列表是根据id进行查询的
    @Override
    public List<ChapterVo> getChapterByCourseId(String courseId) {

        // 1. 根据id查询课程中所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        // 2. 查询出所有的小结
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(wrapperVideo);

        // 创建最终的集合，用于最后的返回结果
        ArrayList<ChapterVo> finalList = new ArrayList<>();
        
        // 3. 遍历所有的章节进行封装
        // 遍历对应的集合
        for (EduChapter eduChapter : eduChapterList) {
            // 将eduChapter 中的值赋值给ChapterVo中【为什么需要新创建再封装？注意一下】
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            // 将创建的对象放到最终的集合中
            finalList.add(chapterVo);

            // 因为一个章节中有很多的小结，所有放在这个循环中进行。来一个嵌套循环
            // 4. 遍历所有的小结进行判断
            // 需要创建一个集合用于封装集合中的小结
            ArrayList<VideoVo> VideoList = new ArrayList<>();
            for (EduVideo eduVideo : eduVideoList) {
                // 小结中的chapterid如果和章节的id相同的话就进行对应的封装
                // 判断：
                if(eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    // 将对应的小结放到对应的章节中去
                    VideoList.add(videoVo);
                }
            }
            // 将封装之后的list放到章节的对象中去
            chapterVo.setChildren(VideoList);
        }
        return finalList;
    }

    // 删除章节的方法【如果章节中有小结的话，就不让删除，所以先进行查询】
    @Override
    public boolean deleteChapter(String chapterId) {
        // 根据章节id查询小结
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        // 当然可以使用eduVideoService.list(wrapper)求出所有的小结，但是，我们现在没有必要求出，只需要知道有没有就可以。
        int count = eduVideoService.count(wrapper);

        if (count > 0) {
            // 如果是有小结的话，不进行删除
            throw new GuliException(20001, "不可以删除");
        } else {
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }
        /*// 下面的是我自己写的，真是够了
        EduVideo byId = eduVideoService.getById(chapterId);*/
    }

    // 根据课程id删除章节
    @Override
    public void removeChapterById(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
