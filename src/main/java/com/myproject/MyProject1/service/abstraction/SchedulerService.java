package com.myproject.MyProject1.service.abstraction;

import com.myproject.MyProject1.dto.InsertScheduler;
import com.myproject.MyProject1.dto.SchedulerGrid;
import org.springframework.data.domain.Page;

public interface SchedulerService {
    Object save(InsertScheduler dto);

    Page<SchedulerGrid> getListScheduler(int page, String search);
}
