package com.techacademy.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.techacademy.entity.Report;
import com.techacademy.service.ReportService;
import com.techacademy.service.UserDetail;

@Controller
public class LoginController {
        private final ReportService service;

        public LoginController(ReportService service) {
            this.service = service;
        }

    /** ログイン画面の表示 */
    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }
    /** Index画面の表示 */
    @GetMapping({ "/" })
    public String getIndex(Model model, @AuthenticationPrincipal UserDetail user) {
        List<Report> reportlist = service.getReportList();
        model.addAttribute("reportlist", reportlist);
        model.addAttribute("kensu", reportlist.size());
        // report/index.htmlに画面遷移
        return "report/index";
    }

}
