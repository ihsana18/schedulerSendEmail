package com.myproject.MyProject1.controller;

import com.myproject.MyProject1.dto.InsertTemplateMessage;
import com.myproject.MyProject1.dto.TemplateMessageGrid;
import com.myproject.MyProject1.service.abstraction.TemplateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/template")
public class TemplateMessageController {

    @Autowired
    private TemplateMessageService service;

    @GetMapping("index")
    public String listTemplate(@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "")String search, Model model){
        Page<TemplateMessageGrid> grid = service.getListTemplate(page,search);
        model.addAttribute("search",search);
        model.addAttribute("currentPage",page);
        model.addAttribute("grid",grid);
        model.addAttribute("breadCrumbs","Template Message Index");
        model.addAttribute("totalPages",grid.getTotalPages());
        return "template/template-index";
    }

    @GetMapping("upsertForm")
    public String upsertForm(@RequestParam(required = false)String currentTemplateName,Model model){
    if(currentTemplateName!=null){
        InsertTemplateMessage dto = service.getTemplateByName(currentTemplateName);
        model.addAttribute("template",dto);
        model.addAttribute("currentTemplateName",currentTemplateName);
        model.addAttribute("type","Update");
    }else{
        InsertTemplateMessage dto = new InsertTemplateMessage();
        model.addAttribute("template",dto);
        model.addAttribute("type","Insert");
    }

    return "template/template-form";
    }

    @PostMapping("upsert")
    public String upsert(@Valid @ModelAttribute("template")InsertTemplateMessage dto){
        service.save(dto);
        return "redirect:/template/index";
    }

    @GetMapping("delete")
    public String delete(@RequestParam(required = true)String templateName){
        service.delete(templateName);
        return "redirect:/template/index";
    }
}
