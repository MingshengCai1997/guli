package com.sheng.eduservice.controller;

import com.sheng.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduLoginController {

    // 登录
    @PostMapping("login")
    public R login() {
        return R.ok().data("token","tocken de data");
    }

    // info
    @GetMapping("info")
    public R info() {
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
