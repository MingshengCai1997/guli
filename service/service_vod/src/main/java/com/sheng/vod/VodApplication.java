package com.sheng.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

// 因为只是对视频操作，没有操作数据库，所以配置文件中没有引入数据库可能会报错
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
// 需要用到自动填充，swagger，相关的返回结果，加上包的扫描规则
@ComponentScan(basePackages = {"com.sheng"})
public class VodApplication {
    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class, args);
    }
}
