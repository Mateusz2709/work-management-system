package com.mateuszmalerek.workmanagement.service;

import com.mateuszmalerek.workmanagement.dto.WorkLogForm;
import com.mateuszmalerek.workmanagement.entity.WorkLog;

import java.util.List;

public interface WorkLogService {

    List<WorkLog> findAll();

    WorkLog findById(Long id);

    WorkLog save(WorkLogForm f);

    void deleteById(Long id);
}
