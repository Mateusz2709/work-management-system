package com.mateuszmalerek.workmanagement.Repository;

import com.mateuszmalerek.workmanagement.entity.CustomerOrder;
import com.mateuszmalerek.workmanagement.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByStatus(OrderStatus status);
}
