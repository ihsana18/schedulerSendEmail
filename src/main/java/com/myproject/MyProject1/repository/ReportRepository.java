package com.myproject.MyProject1.repository;

import com.myproject.MyProject1.dto.ReportGrid;
import com.myproject.MyProject1.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReportRepository extends JpaRepository<Report,String> {
    @Query(value = "select top 1(rep.reportId) FROM Report rep order by rep.reportId desc",nativeQuery = true)
    String getLastId();

    @Query("select new com.myproject.MyProject1.dto.ReportGrid(rp.sentBy,tmp.templateName,rp.email,rp.dateSent,rp.timeSent,rp.bodyMessage ) " +
            "FROM Report rp JOIN rp.templateMessage tmp " +
            "WHERE rp.sentBy LIKE %:search% " +
            "OR tmp.templateName LIKE %:search% " +
            "OR rp.dateSent LIKE %:search% " +
            "OR rp.email LIKE %:search% ")
    Page<ReportGrid> getALl(Pageable pageable, String search);
}
