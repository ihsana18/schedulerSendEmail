package com.myproject.MyProject1.controller;

import com.myproject.MyProject1.dto.Dropdown;
import com.myproject.MyProject1.dto.DropdownDTO;
import com.myproject.MyProject1.dto.InsertTemplateMessage;
import com.myproject.MyProject1.dto.TemplateMessageGrid;
import com.myproject.MyProject1.service.abstraction.TemplateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

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
        List<DropdownDTO> types = Dropdown.dropdownType();
    if(currentTemplateName!=null){
        InsertTemplateMessage dto = service.getTemplateByName(currentTemplateName);
        model.addAttribute("template",dto);
        model.addAttribute("currentTemplateName",currentTemplateName);
        model.addAttribute("type","Update");

    }else{
        InsertTemplateMessage dto = new InsertTemplateMessage();
        model.addAttribute("template",dto);
        model.addAttribute("type","Insert");
        model.addAttribute("attachmentTypes",types);
    }

    return "template/template-form";
    }

    @PostMapping("upsert")
    public String upsert(@Valid @ModelAttribute("template")InsertTemplateMessage dto, BindingResult bindingResult, Model model, RedirectAttributes ra) throws IOException {
            if(bindingResult.hasErrors()){
            if(dto.getCurrentTemplateName()!=null){
                model.addAttribute("template",dto);
                model.addAttribute("currentTemplateName",dto.getCurrentTemplateName());
                model.addAttribute("type","Update");
            }else{
                model.addAttribute("template",dto);
                model.addAttribute("type","Insert");
            }
            return "template/template-form";
        }else{
                service.save(dto);
            return "redirect:/template/index";
        }
    }

    @GetMapping("delete")
    public String delete(@RequestParam(required = true)String templateName){
        service.delete(templateName);
        return "redirect:/template/index";
    }
}
