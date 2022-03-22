package org.vasvari.gradebookweb.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.vasvari.gradebookweb.business.dto.UserDto;
import org.vasvari.gradebookweb.business.dto.UserRole;
import org.vasvari.gradebookweb.business.service.UserService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/users")
    public String listAllUsers(ModelMap model) {
        model.addAttribute("users", service.findAllUsers());

        return "users";
    }

    @GetMapping("/users/new")
    public String showEmptyForm(@RequestParam("role") UserRole role, @RequestParam("schoolActorId") Long schoolActorId, ModelMap model) {
        model.addAttribute("role", role);
        model.addAttribute("schoolActorId", schoolActorId);

        return "user-create";
    }

    @PostMapping("/users/students/{studentId}")
    public String createStudentUser(@PathVariable("studentId") Long studentId, ModelMap model) {
        model.addAttribute("initialCredentials", service.createStudentUser(studentId));

        return "user-create";
    }

    @GetMapping("/users/students/{id}")
    public String navigateToUser(@PathVariable("id") Long studentId, ModelMap model) {
        Optional<UserDto> userMaybe = service.findStudentUser(studentId);
        model.addAttribute("role", UserRole.STUDENT);
        model.addAttribute("schoolActorId", studentId);
        if (userMaybe.isEmpty()) return "redirect:/users/new";
        long userId = userMaybe.get().getId();

        return "redirect:/users/" + userId;
    }

    @GetMapping("/users/{id}")
    public String showFormWithUser(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("editing", true);
        model.addAttribute("roleOptions", UserRole.values());
        model.addAttribute("userDto", service.findUserById(id));

        return "user-details";
    }

}
