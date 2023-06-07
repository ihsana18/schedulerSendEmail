package com.myproject.MyProject1.repository;

import com.myproject.MyProject1.dto.SchedulerGrid;
import com.myproject.MyProject1.entity.Scheduler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SchedulerRepository extends JpaRepository<Scheduler, String> {
    @Query(value = "SELECT TOP 1(sch.schedulerId) FROM Scheduler sch ORDER BY sch.schedulerId DESC ",nativeQuery = true)
    String getLastId();

    @Query("select new com.myproject.MyProject1.dto.SchedulerGrid" +
            "(sc.period,sc.intervalWeek,sc.intervalMonthly,tmp.templateName,sc.sendTime) " +
            "from Scheduler sc " +
            "JOIN sc.templateMessage tmp " +
            "WHERE tmp.templateName LIKE %:search% " +
            "OR sc.period LIKE %:search% ")
    Page<SchedulerGrid> getAll(Pageable pageable, String search);
}