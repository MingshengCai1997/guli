package com.sheng.eduservice.controller;


import com.sheng.commonutils.R;
import com.sheng.eduservice.entity.EduVideo;
import com.sheng.eduservice.entity.vo.VideoInfoForm;
import com.sheng.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author cms
 * @since 2020-12-23
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    // 添加小结
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return R.ok();
    }
    // 删除小结
    //TODO 后面添加视频之后，删除小结的时候将视频一起删除

    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        eduVideoService.removeById(id);
        return R.ok();
    }
    // 修改小结
    // 1. 想修改当然是先将小结查询出来
    @GetMapping("getVideoInfoById/{videoId}")
    public R getVideoInfoById(@PathVariable String videoId) {
        EduVideo eduVideoInfoById = eduVideoService.getById(videoId);
        return R.ok().data("videoInfo", eduVideoInfoById);
    }
    // 2. 进行修改
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }
}

