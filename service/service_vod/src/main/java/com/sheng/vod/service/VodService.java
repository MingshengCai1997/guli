package com.sheng.vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface VodService {
    String uploadVidoAliyun(MultipartFile file);
}
