package com.techacademy.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techacademy.entity.Authentication;
import com.techacademy.entity.Employee;
import com.techacademy.repository.EmployeeRepository;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }
    //パスワード暗号化
    @Autowired
    private PasswordEncoder passwordEncoder;

    // 一覧表示
    public List<Employee> getEmployeeList() {
        return repository.findAll();
    }

    // 詳細表示
    public Employee getEmployee(Integer id) {
        Optional<Employee> option = repository.findById(id);
        Employee employee = option.orElse(null);
        return employee;
    }

    // 登録を行う
    @Transactional
    public Employee saveEmployee(Employee employee) {
        //employeeのデータをセットする
        employee.setCreated_at(LocalDateTime.now());
        employee.setUpdated_at(LocalDateTime.now());
        employee.setDelete_flag(0);
        //authenticationのデータを取得してからセットする
        Authentication authentication = employee.getAuthentication();
        authentication.setRole(employee.getAuthentication().getRole());
        authentication.setPassword(employee.getAuthentication().getPassword());
        authentication.setEmployee(employee);
        //暗号化したパスワードになるように登録
        authentication.setPassword(passwordEncoder.encode(employee.getAuthentication().getPassword()));
        employee.setAuthentication(authentication);
        //リポジトリ（employee）にセーブして返す
        return repository.save(employee);
    }

//更新を行う
    @Transactional
    public Employee updateEmployee(Employee employee) {
        //idのデータを全件から1件取得しupdateとして返す
        Employee update = repository.findById(employee.getId()).get();
        //updateをセットする
        update.setName(employee.getName());
        update.setCreated_at(LocalDateTime.now());
        update.setUpdated_at(LocalDateTime.now());
        update.setDelete_flag(0);
        //authenticationのデータを取得してからセットする
        Authentication authentication = update.getAuthentication();
        authentication.setRole(employee.getAuthentication().getRole());
        //取得したパスワードが空でなければパスワードをセットする
        if (!employee.getAuthentication().getPassword().equals("")) {
            authentication.setPassword(employee.getAuthentication().getPassword());
        }
        //暗号化したパスワードになるように登録
        authentication.setPassword(passwordEncoder.encode(employee.getAuthentication().getPassword()));
        authentication.setEmployee(employee);
        employee.setAuthentication(authentication);
        //リポジトリ（update）にセーブして返す
        return repository.save(update);
    }

    // 削除を行う
    @Transactional
    public void deleteEmployee(Integer idck) {
        Employee employee = getEmployee(idck);
        employee.setDelete_flag(1);
        repository.save(employee);
    }
}
