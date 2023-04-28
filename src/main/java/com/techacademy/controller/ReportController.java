package com.techacademy.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Report;
import com.techacademy.service.ReportService;

@Controller
@RequestMapping("/")
public class ReportController {
    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }
    /** 一覧画面を表示 */
    @GetMapping({"/report", "/report/list"})
    public String getTop(Model model, @AuthenticationPrincipal UserDetails user) {
        List<Report> reportlist = service.getReportList();
        model.addAttribute("reportlist",reportlist);
        model.addAttribute("kensu",reportlist.size());
        // report/list.htmlに画面遷移
        return "report/list";
    }

    /** 詳細画面の表示 */
    @GetMapping("report/detail/{id}/")
    public String getDetail(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("report", service.getReport(id));
        // report/detail.htmlに画面遷移
        return "report/detail";
    }


    // **日報登録画面の表示*/
    @GetMapping("report/register")
    public String getRegister(@ModelAttribute Report report, @AuthenticationPrincipal UserDetails user) {
        // 登録画面に遷移
        return "report/register";
    }

    /** 登録処理 */
    @PostMapping("report/register")
    public String postRegister(Report report, @AuthenticationPrincipal UserDetails user ) {
        service.saveReport(report);

        // 一覧画面にリダイレクト
        return "redirect:/report/list";
    }
}

