package com.mateuszmalerek.workmanagement.service;

import com.mateuszmalerek.workmanagement.Repository.EmployeeRepository;
import com.mateuszmalerek.workmanagement.dto.EmployeeForm;
import com.mateuszmalerek.workmanagement.entity.Employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository theEmployeeRepository) {
        employeeRepository = theEmployeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAllByOrderByLastNameAsc();
    }

    @Override
    public Employee findById(Long id) {

        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id - " + id) );
    }

    @Override
    public Employee save(EmployeeForm f) {
        if (f.id() == null) {
            Employee employee = new Employee();
            employee.setFirstName(f.firstName());
            employee.setLastName(f.lastName());
            employee.setTarget(f.target());
            return employeeRepository.save(employee);
        } else {
            Employee employee = employeeRepository.findById(f.id())
                    .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + f.id()));
            employee.setFirstName(f.firstName());
            employee.setLastName(f.lastName());
            employee.setTarget(f.target());
            return employeeRepository.save(employee);

        }


    }

    @Override
    public void deleteById(Long theId) {
        employeeRepository.deleteById(theId);
    }
}






