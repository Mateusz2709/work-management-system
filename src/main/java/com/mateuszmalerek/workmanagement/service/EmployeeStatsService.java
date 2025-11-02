package com.mateuszmalerek.workmanagement.service;

import com.mateuszmalerek.workmanagement.Repository.WorkLogRepository;
import com.mateuszmalerek.workmanagement.dto.DailySummary;
import com.mateuszmalerek.workmanagement.dto.OrderBreakdown;
import com.mateuszmalerek.workmanagement.entity.CustomerOrder;
import com.mateuszmalerek.workmanagement.entity.Employee;
import com.mateuszmalerek.workmanagement.entity.WorkLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Business logic for calculating daily productivity (expected minutes achieved).
 *
 * Assumptions:
 *  - Each WorkLog == 1 produced item.
 *  - CustomerOrder.expectedMinutes represents minutes PER UNIT.
 *  - Employee.target is minutes PER DAY (e.g. 500).
 *
 * Calculation:
 *   For each order:
 *     unitsDone = number of WorkLogs for that order on the date
 *     minutesPerUnit = order.expectedMinutes (per-unit)
 *     achievedMinutes(order) = unitsDone * minutesPerUnit
 *   Daily achievedMinutes = sum over all orders
 *   percent = round(achieved/target * 100), capped at 100, 0 if target=0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeStatsService {

    private final WorkLogRepository workLogRepository;


    public DailySummary computeDailyExpected(Employee employee, LocalDate date) {
        // Fetch all WorkLogs for the employee on that date.
        // Each WorkLog == 1 unit produced on wl.getCustomerOrder().
        // Use the join-fetch query if you want to avoid N+1:
        // var logs = workLogRepository.findDailyWithOrder(employee.getId(), date);
        List<WorkLog> logs = workLogRepository.findByEmployee_IdAndWorkDate(employee.getId(), date);

        // Group logs by order and count how many logs per order (unitsDone).
        // Map<CustomerOrder, Long>: e.g., {OrderA -> 3, OrderB -> 2}

        Map<CustomerOrder, Long> unitsByOrder = logs.stream()
                .collect(Collectors.groupingBy(WorkLog::getCustomerOrder, Collectors.counting()));

        // Convert the grouped counts into a list of OrderBreakdown rows.
        // For each order: minutesPerUnit = order.getExpectedMinutes() (per-unit),
        // achievedMinutes = unitsDone * minutesPerUnit.

        List<OrderBreakdown> preOrder = unitsByOrder.entrySet().stream()
                .map(e -> {
                    CustomerOrder o = e.getKey();
                    int minutesPerUnit = (o.getExpectedMinutes() == null ? 0 : o.getExpectedMinutes());
                    int unitsDone = e.getValue().intValue();
                    int achieved = unitsDone * minutesPerUnit;

                    return  new OrderBreakdown(
                            o.getId(),
                            o.getOrderNumber(),
                            o.getOrderName(),
                            minutesPerUnit,
                            unitsDone,
                            achieved
                    );
                })
                .toList();

        // Sum achieved minutes across all orders for the day.
       int achievedMinutes = preOrder.stream()
                .mapToInt(OrderBreakdown::achievedMinutes)
                .sum();

        // Employee's target (minutes/day)
        int target = Math.max(0, employee.getTarget());

        // Percentage of the target achieved.
        int percent = (target == 0) ? 0 : Math.round((achievedMinutes * 100.0f) / target);

        return new DailySummary(target, achievedMinutes, percent, logs.size(), preOrder);


    }
}
