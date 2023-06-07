package com.myproject.MyProject1.controller;

import com.myproject.MyProject1.dto.SchedulerGrid;
import com.myproject.MyProject1.service.abstraction.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/scheduler")
public class SchedulerController {

    @Autowired
    private SchedulerService service;

    @GetMapping("/index")
    public String listScheduler(@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "")String search, Model model){
        Page<SchedulerGrid> grid = service.getListScheduler(page,search);
        model.addAttribute("grid",grid);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",grid.getTotalPages());
        model.addAttribute("breadCrumbs","Scheduler Index");
        model.addAttribute("search",search);
        return "scheduler/scheduler-index";
    }
}
