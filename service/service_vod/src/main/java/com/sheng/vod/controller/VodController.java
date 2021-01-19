package com.sheng.vod.controller;

import com.sheng.commonutils.R;
import com.sheng.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    // 上传视频到阿里云方法
    @PostMapping("uploadAliyunVideo")
    public R uploadAliyunVideo(MultipartFile file) {
        // 使用这个获取我们传递的文件
        // 上传视频之后会返回id，我们将id取得
        String videoId = vodService.uploadVidoAliyun(file);
        return R.ok();
    }
}
