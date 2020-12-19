package com.sheng.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtils implements InitializingBean {

    // 读取配置文件中的内容【现在这些值我们是是用不了的，所以就要实现一个接口】
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.file.keyid}")
    private String keyId;
    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;
    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    // 为了可以使用定义公开常量
    public static String END_POIND;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    // 这个方法会在所有操作都执行完成后执行
  @Override
    public void afterPropertiesSet() throws Exception {
      END_POIND = endpoint;
      ACCESS_KEY_ID = keyId;
      ACCESS_KEY_SECRET = keySecret;
      BUCKET_NAME = bucketName;
    }
}
