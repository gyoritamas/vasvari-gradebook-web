package org.vasvari.gradebookweb.web.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.vasvari.gradebookweb.business.util.Problem;

@ControllerAdvice
@Slf4j
public class LoginControllerAdvice {

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {
        log.error("Exception during execution of SpringSecurity application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");

        try {
            String details = errorMessage.substring(errorMessage.indexOf("[") + 1, errorMessage.indexOf("]"));
            Problem problem = Problem.fromJson(details);
            model.addAttribute("problem", problem);
        } catch (Exception e) {
            model.addAttribute("errorMessage", errorMessage);
        }

        return "error";
    }
}
