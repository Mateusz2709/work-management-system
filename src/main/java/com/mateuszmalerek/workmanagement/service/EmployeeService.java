package com.mateuszmalerek.workmanagement.service;

import com.mateuszmalerek.workmanagement.dto.EmployeeForm;
import com.mateuszmalerek.workmanagement.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();

    Employee findById(Long theId);

    Employee save(EmployeeForm f);

    void deleteById(Long theId);

}
