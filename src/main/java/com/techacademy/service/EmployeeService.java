package com.techacademy.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
//    //パスワード暗号化
//    @Autowired
//    private PasswordEncoder passwordEncoder;

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
        employee.setCreated_at(LocalDateTime.now());
        employee.setUpdated_at(LocalDateTime.now());
        employee.setDelete_flag(0);
        Authentication authentication = employee.getAuthentication();
        authentication.setPassword(employee.getAuthentication().getPassword());
        authentication.setEmployee(employee);
        return repository.save(employee);
    }
//更新を行う
    @Transactional
    public Employee updateEmployee(Employee employee) {
        Employee update = repository.findById(employee.getId()).get();
        update.setName(employee.getName());
        update.setCreated_at(LocalDateTime.now());
        update.setUpdated_at(LocalDateTime.now());
        update.setDelete_flag(0);
        Authentication authentication = employee.getAuthentication();
        authentication.setPassword(employee.getAuthentication().getPassword());
        authentication.setEmployee(employee);
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
