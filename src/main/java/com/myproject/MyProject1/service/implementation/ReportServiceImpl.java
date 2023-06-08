package com.myproject.MyProject1.service.implementation;

import com.myproject.MyProject1.dto.ReportGrid;
import com.myproject.MyProject1.repository.ReportRepository;
import com.myproject.MyProject1.service.abstraction.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Override
    public Page<ReportGrid> getList(int page, String search) {
        Pageable pageable = PageRequest.of(page-1,10);
        Page<ReportGrid> reports = reportRepository.getALl(pageable,search);

        return reports;
    }
}
