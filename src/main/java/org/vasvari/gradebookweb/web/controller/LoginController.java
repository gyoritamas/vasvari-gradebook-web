package org.vasvari.gradebookweb.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.vasvari.gradebookweb.business.dto.LoginRequest;
import org.vasvari.gradebookweb.business.service.gateway.LoginGateway;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginGateway loginGateway;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String attemptLogin(@Valid LoginRequest loginRequest, ModelMap model) {
        try {
            loginGateway.login(loginRequest);
        } catch (HttpClientErrorException ex) {
            // TODO: replace
            if (ex.getResponseBodyAsString().contains("Bad credentials")) {
                model.addAttribute("loginError", true);
                return "login";
            }
        }
        model.clear();

        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout() {
        loginGateway.logout();
        return "redirect:/login";
    }
}
