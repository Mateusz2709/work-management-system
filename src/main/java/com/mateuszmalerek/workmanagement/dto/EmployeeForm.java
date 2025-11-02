package com.mateuszmalerek.workmanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record EmployeeForm(
        Long id,
        @NotNull(message = "Employee first name required") String firstName,
        @NotNull(message = "Employee last name required") String lastName,
        @NotNull(message = "Target required")
        @Min(value = 1, message = "Target must be at least 1")
        Integer target
) {

}
