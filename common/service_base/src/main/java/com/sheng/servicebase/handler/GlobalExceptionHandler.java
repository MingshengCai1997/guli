package com.sheng.servicebase.handler;

import com.sheng.commonutils.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public class GlobalExceptionHandler {
    @ExceptionHandler(GuliException.class) // 指定什么异常这个方法会执行
    @ResponseBody //  这个注解就是为了返回数据
    public R error(GuliException e){
        e.printStackTrace();
        return R.error().message(e.getMsg()).code(e.getCode());
    }
}
