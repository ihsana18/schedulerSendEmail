package com.myproject.MyProject1.controller;

import com.myproject.MyProject1.dto.ExportReportToExcel;
import com.myproject.MyProject1.dto.ReportGrid;
import com.myproject.MyProject1.entity.Report;
import com.myproject.MyProject1.service.abstraction.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/report")
@Slf4j
public class ReportController {
    @Autowired
    private ReportService service;

    @GetMapping("/index")
    public String listReport(@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "")String search, Model model){
        Page<ReportGrid> grid = service.getList(page,search);
        model.addAttribute("grid",grid);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",grid.getTotalPages());
        model.addAttribute("search",search);
        model.addAttribute("breadCrumbs","Report Index");
        return "report/report-index";
    }

    @Autowired
    private ExportReportToExcel reportToExcel;
    @GetMapping("/exportForm")
    public String exportForm(Model model){
        model.addAttribute("report",reportToExcel);
        model.addAttribute("type","Export");
        return "report/export-form";
    }

    @PostMapping("/export")
    public void exportToExcel(HttpServletResponse response, @ModelAttribute("report")ExportReportToExcel dto, Model model) throws IOException {
        log.info("start date "+ dto.getStartDate().toString());
        log.info("end date "+dto.getEndDate());
        List<Report> reports = service.getALl();
        // Membuat workbook Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Report");

        // Membuat header kolom
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Report Id");
        headerRow.createCell(1).setCellValue("Sender");
        headerRow.createCell(2).setCellValue("Recipient Email");
        headerRow.createCell(3).setCellValue("DateSent");
        headerRow.createCell(4).setCellValue("TimeSent");
        headerRow.createCell(5).setCellValue("BodyMessage");
        headerRow.createCell(6).setCellValue("TemplateName");

        // Membuat baris data
        int rowNum = 1;
        log.info("reports size "+reports.size());
        for (Report data : reports) {
            if(data.getDateSent().equals(dto.getStartDate())||data.getDateSent().isAfter(dto.getStartDate())==true
           && data.getDateSent().isBefore(dto.getEndDate())==true  || data.getDateSent().equals(dto.getEndDate())){
                log.info("reports size "+reports.size());
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(data.getReportId());
                row.createCell(1).setCellValue(data.getSentBy());
                row.createCell(2).setCellValue(data.getEmail());
                row.createCell(3).setCellValue(String.valueOf(data.getDateSent()));
                row.createCell(4).setCellValue(data.getTimeSent().toString());
                row.createCell(5).setCellValue(data.getBodyMessage());
                row.createCell(6).setCellValue(data.getTemplateMessage().getTemplateName());
            }

        }
        model.addAttribute("grid",reports);

        // Mengatur tipe konten dan header respons
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=reports tanggal: "+dto.getStartDate()+"-"+dto.getEndDate()+".xlsx");

        // Menulis data ke output stream
        workbook.write(response.getOutputStream());
        workbook.close();
//        return "report/report-index";
    }
}
