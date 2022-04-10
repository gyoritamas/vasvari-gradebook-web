package org.vasvari.gradebookweb.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.vasvari.gradebookweb.business.util.UserUtil;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserUtil userUtil;

    @GetMapping
    public String index() {
        if (userUtil.isUnauthenticated()) {
            return "login";
        }

        return "index";
    }

}
