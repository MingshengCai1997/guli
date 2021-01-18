package com.sheng.eduservice.service;

import com.sheng.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sheng.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author cms
 * @since 2020-12-23
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterById(String courseId);
}
