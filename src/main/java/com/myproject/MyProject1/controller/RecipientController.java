package com.myproject.MyProject1.controller;

import com.myproject.MyProject1.dto.DropdownDTO;
import com.myproject.MyProject1.dto.InsertRecipient;
import com.myproject.MyProject1.dto.RecipientAssignTemplate;
import com.myproject.MyProject1.dto.RecipientGrid;
import com.myproject.MyProject1.entity.Recipient;
import com.myproject.MyProject1.entity.TemplateMessage;
import com.myproject.MyProject1.exception.ErrorResponse;
import com.myproject.MyProject1.exception.GlobalExceptionHandling;
import com.myproject.MyProject1.repository.RecipientRepository;
import com.myproject.MyProject1.service.abstraction.RecipientService;
import com.myproject.MyProject1.service.abstraction.TemplateMessageService;
import com.myproject.MyProject1.utility.MonthEnd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/recipient")
@Slf4j
public class RecipientController {
    @Autowired
    private RecipientService service;

    @Autowired
    private TemplateMessageService templateMessageService;

    @GetMapping("/index")
    public String listRecipient(@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "")String search, Model model,@RequestParam(required = false)String message){
        Page<RecipientGrid> grid = service.findAll(page,search);
        model.addAttribute("message",message);
        model.addAttribute("grid",grid);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",grid.getTotalPages());
        model.addAttribute("search",search);
        model.addAttribute("breadCrumbs","Recipient Index");
        MonthEnd.monthEnd();
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
    public String upsert(@Valid @ModelAttribute("recipient")InsertRecipient dto, BindingResult bindingResult,Model model) {
            if (bindingResult.hasErrors()) {
                if (dto.getCurrentName() != null) {
                    model.addAttribute("recipient", dto);
                    model.addAttribute("currentName", dto.getCurrentName());
                    model.addAttribute("type", "Update");
                } else {
                    model.addAttribute("recipient", dto);
                    model.addAttribute("type", "Insert");
                }
                return "recipient/recipient-form";
                }else{
                service.save(dto);
                return "redirect:/recipient/index";

                }
    }

    @GetMapping("delete")
    public String delete(@RequestParam(required = true)String name){
        service.delete(name);
        return "redirect:/recipient/index";
    }


    @GetMapping("/assign-template")
    public String assignTemplate(@RequestParam(required = true) String name,Model model){
        RecipientAssignTemplate assignTemplate = new RecipientAssignTemplate();
        List<TemplateMessage> dropDownTemplate = templateMessageService.dropdownTemplate(name);
        List<TemplateMessage> templateMessages = templateMessageService.listTemplateByName(name);
        model.addAttribute("template",dropDownTemplate);
        model.addAttribute("listTemplate",templateMessages);
        assignTemplate.setName(name);
        model.addAttribute("name",name);
        model.addAttribute("breadCrumbs","Assign Template");
        model.addAttribute("recipient",assignTemplate);
        return "recipient/assign-template";

    }
    @PostMapping("/assign")
    public String assign(@Valid @ModelAttribute("recipient")RecipientAssignTemplate dto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            List<DropdownDTO> dropDownTemplate = templateMessageService.getTemplates();
            model.addAttribute("template",dropDownTemplate);
            model.addAttribute("breadCrumbs","Assign Template");
            return "recipient/assign-template";
        }
        service.assignTemplate(dto);
        redirectAttributes.addAttribute("name",dto.getName());
        return "redirect:/recipient/assign-template";
    }
    @GetMapping("/detach-template")
    public String detachTemplate(@RequestParam(required = true)String name,@RequestParam(required = true)String templateName,RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("name",name);
        service.detach(name,templateName);
        return "redirect:/recipient/assign-template";
    }

    @GetMapping("testRA")
    public String testRA(Model model){
        List<DropdownDTO> templateDropdown = templateMessageService.getTemplates();
        InsertRecipient insertRecipient = new InsertRecipient();
        model.addAttribute("test",insertRecipient);
        model.addAttribute("type","Insert");
        model.addAttribute("templates",templateDropdown);
        return "recipient/recipient-test";
    }

    @PostMapping("test")
    public String test(@ModelAttribute("test")InsertRecipient test,RedirectAttributes ra,Model model) {
        try {
            String message = service.saveTest(test);
//            ra.addFlashAttribute("message",message);
            model.addAttribute("message",message);
            ra.addAttribute("message",message);
            return "redirect:/recipient/index";
        } catch (RuntimeException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/recipient/testRA";
        }
    }
}
