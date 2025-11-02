package com.mateuszmalerek.workmanagement.dto;

import java.util.List;

/**
 * Read-only view model that summarises an employee's daily productivity.
 * - achievedMinutes: sum of expected minutes from all produced items that day
 * - targetMinutes  : employee's daily target in minutes (Employee.target)
 * - percent        : achieved / target (0..100)
 * - logsCount      : how many WorkLog entries exist that day (1 log = 1 item)
 * - perOrder       : breakdown per order (how much each order contributed)
 */
public record DailySummary(
        int targetMinutes,
        int achievedMinutes,
        int percent,
        int logsCount,
        List<OrderBreakdown> perOrder
) {
}
