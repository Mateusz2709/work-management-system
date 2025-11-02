package com.mateuszmalerek.workmanagement.controller;


import com.mateuszmalerek.workmanagement.dto.EmployeeForm;
import com.mateuszmalerek.workmanagement.dto.DailySummary;
import com.mateuszmalerek.workmanagement.entity.Employee;
import com.mateuszmalerek.workmanagement.service.EmployeeService;
import com.mateuszmalerek.workmanagement.service.EmployeeStatsService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/home")
public class EmployeeController {

    private EmployeeService employeeService;

    private EmployeeStatsService statsService;

    public EmployeeController(EmployeeService employeeService, EmployeeStatsService statsService){
        this.employeeService = employeeService;
        this.statsService = statsService;
    }

    @GetMapping("/showFormForAddEmployee")
    public String showFormForAddEmployee(Model theModel){
        Employee theEmployee = new Employee();

        theModel.addAttribute("employee", new EmployeeForm(null, "", "", 0));

        return "employees/employee-form";
    }



    @PostMapping("/saveEmployee")
    public String saveEmployee(@Valid @ModelAttribute("employee") EmployeeForm employeeForm,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()){
            return "employees/employee-form";
        }

        boolean isUpdate = (employeeForm.id() != null);
        employeeService.save(employeeForm);


        redirectAttributes.addFlashAttribute("successMessage", isUpdate ?
                "✅ Employee updated successfully!" : "✅ Employee added successfully!");

        return "redirect:/home/employees";
    }

    @GetMapping("/employees")
    public String listEmployees(Model model){
        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);

        return "employees/list-employees";
    }


    @PostMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam("employeeId") Long id, RedirectAttributes redirectAttributes){
        employeeService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "🗑️ Employee deleted successfully!");
        return "redirect:/home/employees";

    }


    @GetMapping("/employee/{id}")
    public String viewEmployee(
            @PathVariable("id") Long id,
            @RequestParam(value= "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date,
            Model model
            ) {
        // Load employee or throw if not found
        Employee employee = employeeService.findById(id);

        // Default to today if date is null.
        LocalDate thedate = (date == null) ? LocalDate.now() : date;

        // Calculate the summary using the service.
        DailySummary summary = statsService.computeDailyExpected(employee, thedate);

        // Push everything to the model so the view can render it.
        model.addAttribute("employee", employee);
        model.addAttribute("date", thedate);
        model.addAttribute("summary", summary);

        return "employees/view";
    }

    @GetMapping("/showFormForUpdateEmployee")
    public String showFormForUpdateEmployee(@RequestParam("employeeId") Long id, Model model){
        Employee employee = employeeService.findById(id);
        EmployeeForm form = new EmployeeForm(employee.getId(), employee.getFirstName(), employee.getLastName(),
                employee.getTarget());

        model.addAttribute("employee", form);

        return "employees/employee-form";
    }

















}
