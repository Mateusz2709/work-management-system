package com.mateuszmalerek.workmanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderForm(
        @NotNull(message = "Order name required") String orderName,

        @NotNull(message = "Order number required") String orderNumber,

        @NotNull(message = "Expected time required")

        @Min(value = 1, message = "Expected time must be at least 1 minute")
        Integer expectedMinutes,


        @NotNull(message = "Total quantity  required")
        @Min(value = 1, message = "Total quantity must be at least 1")
        Integer totalQuantity

) {
}
