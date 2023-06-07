package com.myproject.MyProject1.controller;

import com.myproject.MyProject1.dto.Dropdown;
import com.myproject.MyProject1.dto.DropdownDTO;
import com.myproject.MyProject1.dto.RegisterUser;
import com.myproject.MyProject1.dto.UserGrid;
import com.myproject.MyProject1.service.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/account")
public class UserController {

    @Autowired
    private UserService service;


    @CrossOrigin
    @GetMapping("/loginForm")
    public String loginForm(Model model) {
        return "account/login-form";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(Model model) {
        return "account/access-denied";
    }

    @GetMapping("/registerForm")
    public String registerForm(Model model,@RequestParam(required = false)String currentUsername) {
        RegisterUser dto = new RegisterUser();
        if(currentUsername!=null){
            RegisterUser user = service.getUserByUsername(currentUsername);
            List<DropdownDTO> roleDropdown = Dropdown.getRoleDropdown();
            model.addAttribute("roleDropdown", roleDropdown);
            model.addAttribute("account", user);
            model.addAttribute("username",currentUsername);
        }else{
            List<DropdownDTO> roleDropdown = Dropdown.getRoleDropdown();
            model.addAttribute("roleDropdown", roleDropdown);
            model.addAttribute("account", dto);

        }
        return "account/register-form";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("account") RegisterUser dto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            List<DropdownDTO> dropdown = Dropdown.getRoleDropdown();
            model.addAttribute("roleDropdown",dropdown);
            return "account/register-form";
        }
        RegisterUser registerUser = service.register(dto);
        return "redirect:/account/index";
    }

    @GetMapping("index")
    public String indexUser(@RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "")String search,Model model){
        Page<UserGrid> userGridPage = service.getListUser(page,search);
        model.addAttribute("search",search);
        model.addAttribute("currentPage",page);
        model.addAttribute("grid",userGridPage.getContent());
        model.addAttribute("totalPages",userGridPage.getTotalPages());
        model.addAttribute("breadCrumbs","User Index");

        return "account/account-index";
    }

    @GetMapping("delete")
    public String delete(@RequestParam(required = true)String username){
        service.delete(username);
        return "redirect:/account/index";
    }

}
