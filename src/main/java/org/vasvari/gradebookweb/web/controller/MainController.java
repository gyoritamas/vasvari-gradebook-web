package org.vasvari.gradebookweb.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.vasvari.gradebookweb.business.jwt.TokenRepository;
import org.vasvari.gradebookweb.business.util.JwtTokenUtil;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final TokenRepository tokenRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping
    public String index(Model model) {
        if (tokenRepository.getToken() == null) {
            return "login";
        }
        model.addAttribute("role", jwtTokenUtil.getUserRoleFromToken(tokenRepository.getToken().getTokenString()));

        return "index";
    }
}
