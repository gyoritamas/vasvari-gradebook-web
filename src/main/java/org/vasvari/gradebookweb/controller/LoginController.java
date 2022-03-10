package org.vasvari.gradebookweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.vasvari.gradebookweb.dto.AssignmentInput;
import org.vasvari.gradebookweb.dto.AssignmentType;
import org.vasvari.gradebookweb.dto.LoginRequest;
import org.vasvari.gradebookweb.service.LoginGateway;

import javax.validation.Valid;

@Controller
public class LoginController {
    @Autowired
    private LoginGateway loginGateway;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String attemptLogin(@Valid LoginRequest loginRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "error";
        }
        loginGateway.login(loginRequest);

        return "redirect:/";
    }

//    @RequestMapping("/login-error")
//    public String loginError(Model model) {
//        model.addAttribute("loginError", true);
//        return "login";
//    }

    @RequestMapping("/logout")
    public String logout() {
        loginGateway.logout();
        return "redirect:/login";
    }
}