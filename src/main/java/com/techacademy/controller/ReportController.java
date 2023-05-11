package com.techacademy.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.techacademy.entity.Report;
import com.techacademy.service.ReportService;
import com.techacademy.service.UserDetail;

@Controller
@RequestMapping("report")
public class ReportController {
	private final ReportService service;

	public ReportController(ReportService service) {
		this.service = service;
	}

	// index自分の日報一覧画面を表示
	@GetMapping({ "/index" })
	public String getIndex(Model model, @AuthenticationPrincipal UserDetail user) {
		List<Report> reportlist = service.getReportList(user.getEmployee());
		model.addAttribute("reportlist", reportlist);
		model.addAttribute("kensu", reportlist.size());
		// report/list.htmlに画面遷移
		return "report/index";
	}

	/** 一覧画面を表示 */
	@GetMapping({ "/list" })
	public String getTop(Model model, @AuthenticationPrincipal UserDetail user) {
		List<Report> reportlist = service.getReportList();
		model.addAttribute("reportlist", reportlist);
		model.addAttribute("kensu", reportlist.size());
		// report/list.htmlに画面遷移
		return "report/list";
	}

	/** 詳細画面の表示 */
	@GetMapping("/detail/{id}/")
	public String getDetail(@PathVariable("id") Integer id, Model model) {
		Report report = service.getReport(id);
		model.addAttribute("report", report);

		// report/detail.htmlに画面遷移
		return "report/detail";
	}

	// **日報登録画面の表示*/
	@GetMapping("/register")
	public String getRegister(@ModelAttribute Report report, @AuthenticationPrincipal UserDetail user, Model model) {
		model.addAttribute("name", user.getUser().getName());
		// 登録画面に遷移
		return "report/register";
	}

	/** 登録処理 */
	@PostMapping("/register")
	public String postRegister(Report report, @AuthenticationPrincipal UserDetail user) {
		report.setEmployee(user.getUser());
		report.setGoodcount(0);
		service.saveReport(report);

		// index画面にリダイレクト
		return "redirect:/report/list";
	}

	// 更新の表示 */
	@GetMapping("/update/{id}/")
	public String getUpdate(@PathVariable("id") Integer id, @AuthenticationPrincipal UserDetail user, Model model) {
		model.addAttribute("report", service.getReport(id));
		model.addAttribute("employee", user.getUser());
		// report/update.htmlに画面遷移
		return "report/update";
	}

	// **更新処理*/
	@PostMapping("/update/{id}")
	public String postUpdate(Report report, @AuthenticationPrincipal UserDetail user) {
		report.setEmployee(user.getUser());
		service.updateReport(report);
		// 一覧画面にリダイレクト
		return "redirect:/report/list";
	}

	// *いいね処理 最大5件まで*/
	@GetMapping("/good/{id}")
	public String getGood(@PathVariable("id") Integer id) {
		Report report = service.getReport(id);
		int count = report.getGoodcount();
		if (count < 5) {
			report.setGoodcount(count + 1);
		}
		service.saveReport(report);
		return "redirect:/report/list";
	}
}
