package com.myproject.MyProject1.service.abstraction;

import com.myproject.MyProject1.dto.ReportGrid;
import org.springframework.data.domain.Page;

public interface ReportService {
    Page<ReportGrid> getList(int page, String search);
}
