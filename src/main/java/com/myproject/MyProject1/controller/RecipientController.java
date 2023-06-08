package com.myproject.MyProject1.controller;

import com.myproject.MyProject1.dto.DropdownDTO;
import com.myproject.MyProject1.dto.InsertRecipient;
import com.myproject.MyProject1.dto.RecipientGrid;
import com.myproject.MyProject1.service.abstraction.RecipientService;
import com.myproject.MyProject1.service.abstraction.TemplateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/recipient")
public class RecipientController {
    @Autowired
    private RecipientService service;

    @Autowired
    private TemplateMessageService templateMessageService;

    @GetMapping("/index")
    public String listRecipient(@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "")String search, Model model){
        Page<RecipientGrid> grid = service.findAll(page,search);
        model.addAttribute("grid",grid);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",grid.getTotalPages());
        model.addAttribute("search",search);
        model.addAttribute("breadCrumbs","Recipient Index");
        return "recipient/recipient-index";
    }

    @GetMapping("/upsertForm")
    public String upsertForm(@RequestParam(required = false)String currentName,Model model){
        List<DropdownDTO> templateDropdown = templateMessageService.getTemplates();
        if(currentName!=null){
            InsertRecipient insertRecipient = service.findByName(currentName);
            model.addAttribute("recipient",insertRecipient);
            model.addAttribute("currentName",currentName);
            model.addAttribute("type","Update");
            model.addAttribute("templates",templateDropdown);
        }else{
            InsertRecipient insertRecipient = new InsertRecipient();
            model.addAttribute("recipient",insertRecipient);
            model.addAttribute("type","Insert");
            model.addAttribute("templates",templateDropdown);
        }
        return "recipient/recipient-form";
    }

    @PostMapping("upsert")
    public String upsert(@Valid @ModelAttribute("recipient")InsertRecipient dto){
        service.save(dto);
        return "redirect:/recipient/index";
    }

    @GetMapping("delete")
    public String delete(@RequestParam(required = true)String name){
        service.delete(name);
        return "redirect:/recipient/index";
    }
}
