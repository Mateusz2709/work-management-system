package com.mateuszmalerek.workmanagement.dto;

/**
 * Read-only view model for contributions per order.
 * - minutesPerUnit : expected minutes for ONE item of this order (per-unit)
 * - unitsDone      : how many items were produced (counts WorkLog entries)
 * - achievedMinutes: unitsDone * minutesPerUnit
 */
public record OrderBreakdown(
        Long orderId,
        String orderNumber,
        String orderName,
        int minutesPerUnit,
        int unitsDone,
        int achievedMinutes
) {
}
