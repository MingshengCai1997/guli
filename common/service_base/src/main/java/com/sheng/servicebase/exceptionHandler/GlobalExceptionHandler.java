package com.sheng.servicebase.exceptionHandler;

import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import com.sheng.commonutils.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }
}
