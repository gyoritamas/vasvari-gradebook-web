package org.vasvari.gradebookweb.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.vasvari.gradebookweb.business.model.request.PasswordChangeRequest;
import org.vasvari.gradebookweb.business.service.UserService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    @GetMapping("/profile")
    public String showProfile(@ModelAttribute PasswordChangeRequest passwordChangeRequest) {
        return "profile";
    }

    @PostMapping("/profile")
    public String changePassword(@Valid PasswordChangeRequest passwordChangeRequest, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) return "profile";

        boolean success = userService.changePassword(passwordChangeRequest);
        model.addAttribute("response", success);

        return "profile";
    }
}
