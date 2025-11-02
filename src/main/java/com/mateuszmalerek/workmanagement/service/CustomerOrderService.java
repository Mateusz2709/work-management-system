package com.mateuszmalerek.workmanagement.service;

import com.mateuszmalerek.workmanagement.dto.OrderForm;
import com.mateuszmalerek.workmanagement.entity.CustomerOrder;
import com.mateuszmalerek.workmanagement.entity.OrderStatus;

import java.util.List;

public interface CustomerOrderService {

    List<CustomerOrder> findAll();

    CustomerOrder findById(Long id);

    CustomerOrder save(OrderForm orderForm);

    void deleteById(Long id);

    List<CustomerOrder> findByStatus(OrderStatus status);
}
