package com.springboot.smartflow.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/login")
    public String loginPage() {
        // 실제 경로인 member 폴더를 포함시켜야 합니다.
        return "member/login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        // 실제 경로인 member 폴더를 포함시켜야 합니다.
        return "member/signup";
    }

    @GetMapping("/main")
    public String mainPage() {
        // main.jsp는 views 바로 아래 있었으니 그대로 둡니다.
        return "main";
    }
}