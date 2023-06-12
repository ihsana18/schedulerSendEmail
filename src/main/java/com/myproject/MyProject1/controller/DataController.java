package com.myproject.MyProject1.controller;

import com.myproject.MyProject1.dto.InsertData;
import com.myproject.MyProject1.service.abstraction.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/data")
public class DataController {
    @Autowired
    private DataService service;

    @GetMapping("/upsertForm")
    public String upsertForm(Model model,Authentication authentication){

        InsertData data = new InsertData();
        data.setInputBy(authentication.getName());
        data.setCreatedDate(LocalDate.now());
        model.addAttribute("data",data);
        model.addAttribute("type","Insert");
        return "data/data-form";
    }

    @PostMapping("upsert")
    public String upsert(@ModelAttribute("data")InsertData dto){
        service.save(dto);
        return "redirect:/template/index";
    }
}
