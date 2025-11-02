package com.mateuszmalerek.workmanagement.Repository;

import com.mateuszmalerek.workmanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // add a method to sort by last name
    public List<Employee> findAllByOrderByLastNameAsc();

}
