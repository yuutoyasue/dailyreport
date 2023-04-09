package com.techacademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {

    /** 一覧画面を表示 */
    @GetMapping("/")
    public String getList(Model modle) {
        // user/list.htmlに画面遷移
        return "/top";
    }
}
