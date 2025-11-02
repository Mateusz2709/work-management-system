package com.mateuszmalerek.workmanagement.Repository;

import com.mateuszmalerek.workmanagement.entity.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {

    // WHERE employee_id=? AND work_date=?
    List<WorkLog> findByEmployee_IdAndWorkDate(Long enployeeId, LocalDate workDate);

    // Optimised: same filter, but JOIN FETCH to pre-load customerOder
    @Query("""
            select w1
            from WorkLog w1
            join fetch w1.customerOrder o
            where w1.employee.id = :empId
            and w1.workDate = :date
            """)
    List<WorkLog> findDailyWithOrder(@Param("empId") Long employeeId, @Param("date") LocalDate workDate);
}
