package com.mateuszmalerek.workmanagement.dto;

import jakarta.validation.constraints.NotNull;

public record WorkLogForm(
        @NotNull(message = "Please select an employee") Long employeeId,
        @NotNull(message = "Please select an order") Long orderId

) {}
