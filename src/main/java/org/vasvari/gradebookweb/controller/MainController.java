package org.vasvari.gradebookweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.vasvari.gradebookweb.jwt.TokenRepository;
import org.vasvari.gradebookweb.util.JwtTokenUtil;

@Controller
public class MainController {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping
    public String index(Model model) {
        if (tokenRepository.getToken() == null) {
            return "login";
        }
        model.addAttribute("role", jwtTokenUtil.getUserRoleFromToken(tokenRepository.getToken().getTokenString()));

        return "index";
    }
}
