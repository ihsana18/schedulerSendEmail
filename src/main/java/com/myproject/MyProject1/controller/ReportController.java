package com.myproject.MyProject1.controller;

import com.myproject.MyProject1.dto.ReportGrid;
import com.myproject.MyProject1.service.abstraction.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/report")
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
}
