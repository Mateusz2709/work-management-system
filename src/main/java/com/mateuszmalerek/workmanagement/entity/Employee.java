package com.mateuszmalerek.workmanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "employees",
        indexes = {
                @Index(name = "ix_employee_name", columnList = "last_name, first_name")
        }
)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="first_name", nullable = false, length = 60)
    private String firstName;

    @Column(name="last_name", nullable = false, length = 60)
    private String lastName;

    @Column(name="target", nullable = false)
    private int target;

    public Employee() { }

    public Employee(String firstName, String lastName, int target) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.target = target;
    }

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }
}





