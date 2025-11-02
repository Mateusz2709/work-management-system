# Work Management System

A full-stack **Spring Boot + Thymeleaf** web application for managing **employees**, **customer orders**, and **daily work logs**.

It helps supervisors track productivity, monitor order completion, and measure each employee’s progress toward their daily performance targets.


This project demonstrates a practical factory-style management system built with **Spring Boot**.

**Key Concept:**
- Every “Work Log” represents one completed production unit.
- Each “Customer Order” defines the expected time per unit (`expectedMinutes`).
- Every “Employee” has a daily target in minutes.
- The system automatically calculates whether the target was reached and shows a detailed summary per employee.


**Backend:** Java 17 · Spring Boot 3 · Spring MVC · Spring Data JPA · Hibernate ORM  
**Frontend:** Thymeleaf · Bootstrap 5.3 · HTML5 · CSS3  
**Database:** MySQL or H2 (for demo/testing)  
**Build Tools:** Maven · Lombok  
**IDE:** IntelliJ IDEA or VS Code



### 👨‍🏭 Employee Management
- Add, update, and delete employees.
- Assign a daily target (minutes or units).
- View daily productivity summaries.

### 📦 Customer Orders
- Create new orders with expected time per unit and total quantity.
- Automatically mark as *COMPLETED* when all units are done.
- Filter orders by status (Pending / Completed).

### 🕒 Work Logs
- Record every finished unit.
- Automatically increments the order’s progress.
- Displays date/time and success feedback.

### 📊 Daily Summary
- Calculates total achieved minutes.
- Compares against the target (percentage complete).
- Shows per-order breakdown and progress bar visualization.


## Architecture

**Controllers**
- EmployeeController
- CustomerOrderController
- WorkLogController

**Services**
- EmployeeService
- CustomerOrderService
- WorkLogService
- EmployeeStatsService (handles performance calculation)

**Repositories**
- EmployeeRepository
- CustomerOrderRepository
- WorkLogRepository

**Entities**
- Employee
- CustomerOrder
- WorkLog
- OrderStatus (enum)

**DTOs**
- EmployeeForm, OrderForm, WorkLogForm
- DailySummary, OrderBreakdown

## Database Design

**employees**
- id, first_name, last_name, target

**orders**
- id, order_number, order_name, expected_minutes, total_quantity, completed_quantity, status

**work_logs**
- id, employee_id, order_id, work_date

**Relationships**
- One Employee → many WorkLogs  
- One Order → many WorkLogs

## Run Locally

1. Clone the project  
   ```bash
   git clone https://github.com/<your-username>/workforce-management.git
   cd workforce-management
   ```

2. Configure the database in `src/main/resources/application.properties`  
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/workforce_db
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

3. Run the app  
   ```bash
   mvn spring-boot:run
   ```

4. Open in browser:  
   [http://localhost:8080/home/showFormForAddEmployee](http://localhost:8080/home/showFormForAddEmployee)



## Usage/Examples

1. Add an employee (e.g., John Smith, target 500 minutes/day).  
2. Create customer orders (e.g., “O-CHAIR-100” with 100 min per unit).  
3. Record work logs for each completed unit.  
4. The app automatically updates:
   - Order’s completed quantity and status.
   - Employee’s daily performance summary.



## Lessons Learned

- Building a full MVC architecture with Spring Boot  
- Using DTOs for form validation and cleaner controllers  
- Managing One-to-Many relationships in JPA  
- Applying Bootstrap and Thymeleaf for modern UI  
- Calculating data dynamically with Java Streams  
- Handling optimistic locking in database updates



## Authors

**Mateusz Malerek**  
BSc Software Engineering – University of Bolton  
📍 Blackburn, United Kingdom  

💼 [GitHub](https://github.com/Mateusz2709) • [LinkedIn](https://www.linkedin.com/in/mateusz-m-7b6b66301/)


