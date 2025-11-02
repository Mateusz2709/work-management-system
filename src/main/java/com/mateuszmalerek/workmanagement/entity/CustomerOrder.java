package com.mateuszmalerek.workmanagement.entity;

import jakarta.persistence.*;

/**
 * Represents a customer's production order.
 *
 * Each order can have many WorkLogs recorded against it.
 * When completedQuantity reaches totalQuantity, its status becomes COMPLETED.
 */
@Entity
@Table(
        name = "orders",
        uniqueConstraints = @UniqueConstraint(name = "uk_order_number", columnNames = "order_number")
)
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Unique order number */
    @Column(name = "order_number", nullable = false, length = 50)
    private String orderNumber;

    /** Descriptive order name */
    @Column(name = "order_name", nullable = false, length = 100)
    private String orderName;

    /** Expected time to complete (in minutes) */
    @Column(name = "expected_minutes", nullable = false)
    private Integer expectedMinutes;

    /** Total quantity to be completed */
    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity = 10;

    /** Number of units already completed */
    @Column(name = "completed_quantity", nullable = false)
    private Integer completedQuantity = 0;

    /** Order status */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private OrderStatus status = OrderStatus.PENDING;

    /** Used for optimistic locking */
    @Version
    @Column(name = "version")
    private Long version;

    // ----------------------------------------------------------
    // Constructors
    // ----------------------------------------------------------
    public CustomerOrder() { }

    public CustomerOrder(String orderNumber, String orderName, Integer expectedMinutes) {
        this.orderNumber = orderNumber;
        this.orderName = orderName;
        this.expectedMinutes = expectedMinutes;
        this.status = OrderStatus.PENDING;
    }

    // ----------------------------------------------------------
    // Business Logic
    // ----------------------------------------------------------
    /**
     * Increments completedQuantity by one.
     * When completedQuantity equals totalQuantity, marks order COMPLETED.
     */
    public void incrementProgress() {
        if (completedQuantity < totalQuantity) {
            completedQuantity++;
            if (completedQuantity.equals(totalQuantity)) {
                status = OrderStatus.COMPLETED;
            }
        }
    }

    // ----------------------------------------------------------
    // Getters / Setters
    // ----------------------------------------------------------
    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }

    public String getOrderName() { return orderName; }
    public void setOrderName(String orderName) { this.orderName = orderName; }

    public Integer getExpectedMinutes() { return expectedMinutes; }
    public void setExpectedMinutes(Integer expectedMinutes) { this.expectedMinutes = expectedMinutes; }

    public Integer getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(Integer totalQuantity) { this.totalQuantity = totalQuantity; }

    public Integer getCompletedQuantity() { return completedQuantity; }
    public void setCompletedQuantity(Integer completedQuantity) { this.completedQuantity = completedQuantity; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public Long getVersion() { return version; }

    @Override
    public String toString() {
        return String.format(
                "CustomerOrder[id=%d, orderNumber=%s, name=%s, total=%d, done=%d, status=%s]",
                id, orderNumber, orderName, totalQuantity, completedQuantity, status
        );
    }
}
