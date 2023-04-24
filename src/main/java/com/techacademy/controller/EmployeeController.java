package com.techacademy.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.techacademy.entity.Employee;
import com.techacademy.service.EmployeeService;

@Controller
@RequestMapping("/")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    /** 一覧画面を表示 */
    @GetMapping({"/", "/employee/list"})
    public String getTop(Model model) {
        List<Employee> employeelist = service.getEmployeeList();
        model.addAttribute("employeelist",employeelist);
        model.addAttribute("kensu",employeelist.size());
        // employee/list.htmlに画面遷移
        return "employee/list";
    }

    /** 詳細画面の表示 */
    @GetMapping("employee/detail/{id}/")
    public String getDetail(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("employee", service.getEmployee(id));
        // employee/detail.htmlに画面遷移
        return "employee/detail";
    }

    // **従業員登録画面の表示*/
    @GetMapping("employee/register")
    public String getRegister(@ModelAttribute Employee emloyee) {
        // 登録画面に遷移
        return "employee/register";
    }

    /** 登録処理 */
    @PostMapping("employee/register")
    public String postRegister(Employee employee) {
        service.saveEmployee(employee);
        // 一覧画面にリダイレクト
        return "redirect:/employee/list";
    }

    // **更新画面の表示*/
    @GetMapping("employee/update/{id}")
    public String getEmployee(@PathVariable("id") Integer id, Model model) {
        //employeeにサービスemployee(id)から取得する
    Employee employee = service.getEmployee(id);
    //空のパスワードをセットする
    employee.getAuthentication().setPassword("");
        model.addAttribute("employee", employee);
        // 更新画面に遷移
        return "employee/update";
    }

    // **更新処理*/
    @PostMapping("employee/update/{id}")
    public String postEmployee(Employee employee) {
        service.updateEmployee(employee);
   // 一覧画面にリダイレクト
        return "redirect:/employee/list";
    }

    // **削除*/
    @GetMapping("employee/delete/{id}")
    public String getDelete(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("employee", service.getEmployee(id));
        service.deleteEmployee(id);
        // 一覧画面に遷移
        return "redirect:/employee/list";
    }
}
