package com.mateuszmalerek.workmanagement.controller;


import com.mateuszmalerek.workmanagement.dto.OrderForm;
import com.mateuszmalerek.workmanagement.entity.CustomerOrder;
import com.mateuszmalerek.workmanagement.entity.OrderStatus;
import com.mateuszmalerek.workmanagement.service.CustomerOrderService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/home")
public class CustomerOrderController {

    private CustomerOrderService customerOrderService;


    public CustomerOrderController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @GetMapping("/showFormForAddOrder")
    public String showFormForAddOrder(Model model) {
        model.addAttribute("order", new OrderForm("", "", 0, 0));

        return "orders/order-form";
    }

    @PostMapping("/saveOrder")
    public String saveOrder(@Valid @ModelAttribute("order") OrderForm orderForm, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "orders/order-form";
        }

        redirectAttributes.addFlashAttribute("successMessage", "✅ Customer order added successfully!");

        customerOrderService.save(orderForm);
        return "redirect:/home/showFormForAddOrder";
    }


    @GetMapping("/orders")
    public String listOrders(@RequestParam(required = false) OrderStatus status, Model model) {
        List<CustomerOrder> orders;

        if (status != null){
            orders = customerOrderService.findByStatus(status);
        } else {
            orders = customerOrderService.findAll();
        }



        model.addAttribute("orders", orders);
        model.addAttribute("selectedStatus", status);


        return "orders/list-orders";
    }


    @PostMapping("/deleteOrder")
    public String deleteOrder(@RequestParam("orderId") Long id, RedirectAttributes redirectAttributes){
        customerOrderService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "🗑️ Order deleted successfully!");
        return "redirect:/home/orders";
    }

}
