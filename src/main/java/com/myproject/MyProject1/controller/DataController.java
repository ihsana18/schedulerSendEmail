package com.myproject.MyProject1.controller;

import com.myproject.MyProject1.dto.InsertData;
import com.myproject.MyProject1.service.abstraction.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/data")
public class DataController {
    @Autowired
    private DataService service;

    @GetMapping("/upsertForm")
    public String upsertForm(Model model){
        InsertData data = new InsertData();
        model.addAttribute("data",data);
        model.addAttribute("type","Insert");
        return "data/data-form";
    }
}
