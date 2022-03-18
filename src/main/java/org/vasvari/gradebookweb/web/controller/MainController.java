package org.vasvari.gradebookweb.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.vasvari.gradebookweb.business.jwt.TokenRepository;
import org.vasvari.gradebookweb.business.util.UserUtil;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final TokenRepository tokenRepository;
    private final UserUtil userUtil;

    @GetMapping
    public String index(Model model) {
        if (tokenRepository.getToken() == null) {
            return "login";
        }
        model.addAttribute("userDetails", printUserDetails());
        model.addAttribute("userRole", userUtil.userRole().name());

        return "index";
    }

    private String printUserDetails() {
        return String.format("%s (%s)", userUtil.username(), userUtil.userRole().name().toLowerCase());
    }
}
