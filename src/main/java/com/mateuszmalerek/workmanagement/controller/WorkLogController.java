package com.mateuszmalerek.workmanagement.controller;

import com.mateuszmalerek.workmanagement.dto.WorkLogForm;
import com.mateuszmalerek.workmanagement.entity.OrderStatus;
import com.mateuszmalerek.workmanagement.service.CustomerOrderService;
import com.mateuszmalerek.workmanagement.service.EmployeeService;
import com.mateuszmalerek.workmanagement.service.WorkLogService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("home")
public class WorkLogController {

    private final WorkLogService workLogService;
    private final EmployeeService employeeService;
    private final CustomerOrderService customerOrderService;

    public WorkLogController(WorkLogService workLogService,
                             EmployeeService employeeService,
                             CustomerOrderService customerOrderService) {
        this.workLogService = workLogService;
        this.employeeService = employeeService;
        this.customerOrderService = customerOrderService;
    }

    // Show form
    @GetMapping("/showFormForWorkLog")
    public String showFormForWorkLog(Model model) {
        WorkLogForm form = new WorkLogForm(null, null);

        model.addAttribute("form", form);
        model.addAttribute("employees", employeeService.findAll());

        var activeOrders = customerOrderService.findAll().stream()
                        .filter(order -> order.getStatus() != OrderStatus.COMPLETED)
                                .toList();

        model.addAttribute("orders", activeOrders);



        model.addAttribute("currentDate", LocalDate.now());
        model.addAttribute("currentTime", LocalTime.now().truncatedTo(ChronoUnit.MINUTES));
        return "worklog/showWorkLogForm";
    }

    // Handle submit
    @PostMapping("/saveWorkLog")
    public String saveWorkLog(@Valid @ModelAttribute("form") WorkLogForm theWorkLog,
                              BindingResult bindingResult,
                              Model model, RedirectAttributes redirectAttributes) {

       if (bindingResult.hasErrors()){
           model.addAttribute("employees", employeeService.findAll());
           model.addAttribute("orders", customerOrderService.findAll());
           model.addAttribute("currentDate", LocalDate.now());
           model.addAttribute("currentTime", LocalTime.now().truncatedTo(ChronoUnit.MINUTES));
           return "worklog/showWorkLogForm";
       }

        redirectAttributes.addFlashAttribute("successMessage", "✅ Work log saved successfully!");

        workLogService.save(theWorkLog);
        return "redirect:/home/showFormForWorkLog";
    }
}
