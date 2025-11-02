package com.mateuszmalerek.workmanagement.service;

import com.mateuszmalerek.workmanagement.Repository.CustomerOrderRepository;
import com.mateuszmalerek.workmanagement.dto.OrderForm;
import com.mateuszmalerek.workmanagement.entity.CustomerOrder;
import com.mateuszmalerek.workmanagement.entity.OrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;

    public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository){
        this.customerOrderRepository = customerOrderRepository;
    }

    @Override
    public List<CustomerOrder> findAll() {
        return customerOrderRepository.findAll();
    }

    @Override
    public CustomerOrder findById(Long id) {
        // lambda expression returns a new RuntimeException when Optional is empty.
        // lambda expression implements the Supplier<Throwable> functional interface.
        return customerOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer order not found with id - " + id));
    }

    @Override
    public CustomerOrder save(OrderForm orderForm) {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setOrderName(orderForm.orderName());
        customerOrder.setOrderNumber(orderForm.orderNumber());
        customerOrder.setExpectedMinutes(orderForm.expectedMinutes());
        customerOrder.setTotalQuantity(orderForm.totalQuantity());
        return customerOrderRepository.save(customerOrder);
    }



    @Override
    public void deleteById(Long id) {
        customerOrderRepository.deleteById(id);

    }

    @Override
    public List<CustomerOrder> findByStatus(OrderStatus status) {
        return customerOrderRepository.findByStatus(status);
    }
}
