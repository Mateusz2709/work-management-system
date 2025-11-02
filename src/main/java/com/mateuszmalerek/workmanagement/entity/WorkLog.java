package com.mateuszmalerek.workmanagement.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.ZoneId;

@Entity
@Table(name = "work_logs")
public class WorkLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The employee who performed the work */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee; // leave null by default so missing select is caught

    /** The order that was worked on */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private CustomerOrder customerOrder; // leave null by default

    /** The date when work was done */
    @Column(name = "work_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate workDate;


    // ----------------------------------------------------------
    // Constructors
    // ----------------------------------------------------------
    public WorkLog() {}

    public WorkLog(Employee employee, CustomerOrder customerOrder,
                   LocalDate workDate) {
        this.employee = employee;
        this.customerOrder = customerOrder;
        this.workDate = workDate;

    }

    // ----------------------------------------------------------
    // Getters and Setters
    // ----------------------------------------------------------
    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public CustomerOrder getCustomerOrder() { return customerOrder; }
    public void setCustomerOrder(CustomerOrder customerOrder) { this.customerOrder = customerOrder; }

    public LocalDate getWorkDate() { return workDate; }
    public void setWorkDate(LocalDate workDate) { this.workDate = workDate; }



    // ----------------------------------------------------------
    // Utility Methods
    // ----------------------------------------------------------
    @PrePersist
    public void setDefaultDateIfMissing() {
        if (workDate == null) {
            workDate = LocalDate.now(ZoneId.of("Europe/London"));
        }
    }


    @Override
    public String toString() {
        return String.format(
                "WorkLog[id=%d, employee=%s, order=%s, date=%s]",
                id,
                employee != null ? employee.getLastName() : "N/A",
                customerOrder != null ? customerOrder.getOrderNumber() : "N/A",
                workDate
        );
    }

}
