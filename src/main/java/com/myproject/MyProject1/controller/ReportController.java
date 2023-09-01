package com.myproject.MyProject1.controller;

import com.myproject.MyProject1.dto.Dropdown;
import com.myproject.MyProject1.dto.DropdownDTO;
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
import java.time.LocalDate;
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
        List<DropdownDTO> month = Dropdown.getMonth();
        ExportReportToExcel reportToExcel =new ExportReportToExcel();
        model.addAttribute("month",month);
        model.addAttribute("report",reportToExcel);
        model.addAttribute("grid",grid);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",grid.getTotalPages());
        model.addAttribute("search",search);
        model.addAttribute("breadCrumbs","Report Index");
        return "report/report-index";
    }
    @PostMapping("/export")
    public void exportToExcel(HttpServletResponse response, @ModelAttribute("report")ExportReportToExcel dto, Model model) throws IOException {
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
            if(data.getDateSent().getMonth().toString().toLowerCase().equals(dto.getMonth().toLowerCase())){
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
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }
        model.addAttribute("report",reports);

        // Mengatur tipe konten dan header respons
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=reports bulan "+dto.getMonth()+".xlsx");

        // Menulis data ke output stream
        workbook.write(response.getOutputStream());
        workbook.close();
//        return "report/report-index";
    }
}
