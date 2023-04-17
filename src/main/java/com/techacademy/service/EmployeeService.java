package com.techacademy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techacademy.entity.Employee;
import com.techacademy.repository.EmployeeRepository;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

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
        return repository.save(employee);
    }

    // 削除を行う
    @Transactional
    public void deleteEmployee(Integer idck) {
            Employee employee = getEmployee(idck);
            employee.setDelete_flag(1);
            repository.save(employee);
        }
}
