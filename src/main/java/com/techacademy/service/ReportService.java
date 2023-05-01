package com.techacademy.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.repository.ReportRepository;

@Service
public class ReportService {
    private final ReportRepository repository;

    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }
    //index自分の日報を表示
    public List<Report> getReportList(Employee employee) {
        return repository.findByEmployee(employee);
    }

    // 一覧表示
    public List<Report> getReportList() {
        return repository.findAll();
    }

    // 詳細表示
    public Report getReport(Integer id) {
        Optional<Report> option = repository.findById(id);
        Report report = option.orElse(null);
        return report;
    }

    // 登録を行う
    @Transactional
    public Report saveReport(Report report) {
        // reportのデータをセットする
        report.setReport_date(report.getReport_date());
        report.setTitle(report.getTitle());
        report.setContent(report.getContent());
        report.setCreated_at(LocalDateTime.now());
        report.setUpdated_at(LocalDateTime.now());

        Employee employee = report.getEmployee();

        report.setEmployee(employee);
        // リポジトリ（report）にセーブして返す
        return repository.save(report);
    }
    //更新を行う
    @Transactional
    public Report updateReport(Report report) {
        Report update = repository.findById(report.getId()).get();
        update.setReport_date(report.getReport_date());
        update.setTitle(report.getTitle());
        update.setContent(report.getContent());
        update.setUpdated_at(LocalDateTime.now());

        Employee employee = update.getEmployee();
        employee.setName(report.getEmployee().getName());

        report.setEmployee(employee);
        return repository.save(update);
    }

}
