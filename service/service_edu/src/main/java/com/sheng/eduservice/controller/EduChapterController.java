package com.sheng.eduservice.controller;


import com.sheng.commonutils.R;
import com.sheng.eduservice.entity.EduChapter;
import com.sheng.eduservice.entity.chapter.ChapterVo;
import com.sheng.eduservice.service.EduChapterService;
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
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    // 课程大纲列表，根据课程的id进行查询【返回的是所有的章节】
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = eduChapterService.getChapterByCourseId(courseId);
        return R.ok().data("allChapterVideo", list);
    }

    // 添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    // 想要修改，就要根据id进行回显，然后才是修改
    // 根据id查询【返回的是章节的对象，返回的仅仅是一个章节】
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        EduChapter chapterInfoById = eduChapterService.getById(chapterId);
        return R.ok().data("chapter",chapterInfoById);
    }
    // 修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    // 删除章节
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean flag = eduChapterService.deleteChapter(chapterId);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }


}

