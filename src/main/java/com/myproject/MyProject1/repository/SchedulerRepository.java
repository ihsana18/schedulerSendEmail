package com.myproject.MyProject1.repository;

import com.myproject.MyProject1.dto.InsertScheduler;
import com.myproject.MyProject1.dto.SchedulerGrid;
import com.myproject.MyProject1.entity.Scheduler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SchedulerRepository extends JpaRepository<Scheduler, String> {
    @Query(value = "SELECT TOP 1(sch.schedulerId) FROM Scheduler sch ORDER BY sch.schedulerId DESC ",nativeQuery = true)
    String getLastId();

    @Query("select new com.myproject.MyProject1.dto.SchedulerGrid" +
            "(sc.schedulerName,sc.period,sc.intervalWeek,sc.intervalMonthly,tmp.templateName,sc.sendTime) " +
            "from Scheduler sc " +
            "JOIN sc.templateMessage tmp " +
            "WHERE tmp.templateName LIKE %:search% " +
            "OR sc.period LIKE %:search% ")
    Page<SchedulerGrid> getAll(Pageable pageable, String search);

    @Query("select sch from Scheduler sch WHERE sch.schedulerName = :currentSchedulerName")
    Scheduler findByName(String currentSchedulerName);

    @Query("select new com.myproject.MyProject1.dto.InsertScheduler(sch.schedulerName,sch.schedulerName,sch.period,sch.intervalWeek,sch.intervalMonthly,tmp.templateName,sch.sendTime) from Scheduler sch JOIN sch.templateMessage tmp WHERE sch.schedulerName = :currentSchedulerName")
    InsertScheduler getByName(String currentSchedulerName);


    @Query("select count(sch) FROM Scheduler sch where sch.schedulerName = :valueSchedulerName")
    int countByName(String valueSchedulerName);


    @Query("select sch from Scheduler sch WHERE sch.schedulerName = :schedulerName")
    List<Scheduler> findBySchName(String schedulerName);
}