package com.sheng.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.sheng.oss.service.OssService;
import com.sheng.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    // 上传文件到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POIND;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            // 获取文件的名称
            String filename = file.getOriginalFilename();

            // 在文件名称中加一个随机的唯一的值[但是有很多的 “——”]--->【这样就解决了重复文件覆盖前一个文件】
            String uuid = UUID.randomUUID().toString().replace("-","");
            filename = uuid + filename;
            // 将文件按照日期进行分类
            // 获取当前的时间日期
            String dataPath = new DateTime().toString("yyyy/MM/dd");
            // 再进行拼接
            filename = dataPath+"/"+filename;

            // 调用oss中的方法实现上传
            // 第一个参数：bucket名称 第二个参数：上传到oss文件路径和文件名称 /aa/bb/1.jpg
            ossClient.putObject(bucketName, filename, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            // 上传之后的路径进行返回
            // 需要手动拼接出来再进行返回
            //https://edu-0530.oss-cn-beijing.aliyuncs.com/QQ%E6%88%AA%E5%9B%BE20201219123139.png
            String url = "https://"+bucketName+"."+endpoint+"/"+filename;
            return url;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
     }
}
