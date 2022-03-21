package org.vasvari.gradebookweb.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.vasvari.gradebookweb.business.service.UserService;
import org.vasvari.gradebookweb.business.util.UserUtil;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserUtil userUtil;
    private final UserService service;

    @GetMapping("/users")
    public String listAllUsers(ModelMap model){
        if (!userUtil.isUserLoggedIn()) {
            return "login";
        }
        model.addAttribute("users", service.findAllUsers());

        return "users";
    }

}
