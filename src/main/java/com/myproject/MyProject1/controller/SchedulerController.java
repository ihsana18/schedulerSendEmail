package com.myproject.MyProject1.controller;

import com.myproject.MyProject1.dto.Dropdown;
import com.myproject.MyProject1.dto.DropdownDTO;
import com.myproject.MyProject1.dto.InsertScheduler;
import com.myproject.MyProject1.dto.SchedulerGrid;
import com.myproject.MyProject1.entity.TemplateMessage;
import com.myproject.MyProject1.service.abstraction.SchedulerService;
import com.myproject.MyProject1.service.abstraction.TemplateMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/scheduler")
@Slf4j
public class SchedulerController {

    @Autowired
    private SchedulerService service;

    @Autowired
    private TemplateMessageService templateMessageService;

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

    @GetMapping("/upsertForm")
    public String upsertForm(@RequestParam(required = false)String currentSchedulerName,Model model){
        List<DropdownDTO> monthlyDropdown = Dropdown.getMonthly();
        List<DropdownDTO> templateDropdown = templateMessageService.getTemplates();
        if(currentSchedulerName!=null){
            InsertScheduler sched = service.getSchedulerByName(currentSchedulerName);
            model.addAttribute("month",monthlyDropdown);
            model.addAttribute("type","Update");
            model.addAttribute("scheduler",sched);
            model.addAttribute("currentSchedulerName",currentSchedulerName);
            model.addAttribute("templates",templateDropdown);
        }else{
            InsertScheduler insertScheduler = new InsertScheduler();
            insertScheduler.setSendTime(LocalTime.now().toString());
            model.addAttribute("templates",templateDropdown);
            model.addAttribute("month",monthlyDropdown);
            model.addAttribute("scheduler",insertScheduler);
            model.addAttribute("type","Insert");
        }
        return "scheduler/scheduler-form";
    }

    @PostMapping("upsert")
    public String upsert(@Valid @ModelAttribute("scheduler")InsertScheduler dto, BindingResult bindingResult, Model model){
        List<DropdownDTO> monthlyDropdown = Dropdown.getMonthly();
        List<DropdownDTO> templateDropdown = templateMessageService.getTemplates();
        if (bindingResult.hasErrors()){
            if(dto.getCurrentSchedulerName()!=null){
                model.addAttribute("month",monthlyDropdown);
                model.addAttribute("type","Update");
                model.addAttribute("scheduler",dto);
                model.addAttribute("currentSchedulerName",dto.getCurrentSchedulerName());
                model.addAttribute("templates",templateDropdown);
            }else{
//                InsertScheduler insertScheduler = new InsertScheduler();
                model.addAttribute("templates",templateDropdown);
                model.addAttribute("month",monthlyDropdown);
                model.addAttribute("scheduler",dto);
                model.addAttribute("type","Insert");
            }
            return "scheduler/scheduler-form";
        }else{
            service.save(dto);
            return "redirect:/scheduler/index";
        }
    }

    @GetMapping("delete")
    public String delete(@RequestParam(required = true)String schedulerName){
        service.delete(schedulerName);
        return "redirect:/scheduler/index";
    }
}
