package com.mateuszmalerek.workmanagement.service;

import com.mateuszmalerek.workmanagement.Repository.CustomerOrderRepository;
import com.mateuszmalerek.workmanagement.Repository.EmployeeRepository;
import com.mateuszmalerek.workmanagement.Repository.WorkLogRepository;
import com.mateuszmalerek.workmanagement.dto.WorkLogForm;
import com.mateuszmalerek.workmanagement.entity.WorkLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WorkLogServiceImpl implements WorkLogService {

    private final WorkLogRepository workLogRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final EmployeeRepository employeeRepository;

    public WorkLogServiceImpl(WorkLogRepository workLogRepository,
                              CustomerOrderRepository customerOrderRepository,
                              EmployeeRepository employeeRepository) {
        this.workLogRepository = workLogRepository;
        this.customerOrderRepository = customerOrderRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<WorkLog> findAll() {
        return workLogRepository.findAll();
    }

    @Override
    public WorkLog findById(Long id) {
        return workLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkLog not found with id - " + id));
    }

    @Override
    public WorkLog save(WorkLogForm f) {

        var employee = employeeRepository.findById(f.employeeId())
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Employee not found"));
        var order = customerOrderRepository.findById(f.orderId())
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Order not found"));

        // Business rule: each WorkLog == +1 unit
        order.incrementProgress();

        var wl = new WorkLog();
        wl.setEmployee(employee);
        wl.setCustomerOrder(order);


        return workLogRepository.save(wl);
    }

    @Override
    public void deleteById(Long id) {
        workLogRepository.deleteById(id);
    }
}
