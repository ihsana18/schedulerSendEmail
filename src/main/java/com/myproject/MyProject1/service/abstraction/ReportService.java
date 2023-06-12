package com.myproject.MyProject1.service.abstraction;

import com.myproject.MyProject1.dto.ReportGrid;
import com.myproject.MyProject1.entity.Report;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    Page<ReportGrid> getList(int page, String search);


    List<Report> getALl();
}
