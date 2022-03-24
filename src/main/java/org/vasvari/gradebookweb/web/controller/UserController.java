package org.vasvari.gradebookweb.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.vasvari.gradebookweb.business.dto.UserDto;
import org.vasvari.gradebookweb.business.dto.UserRole;
import org.vasvari.gradebookweb.business.dto.dataTypes.UsernameInput;
import org.vasvari.gradebookweb.business.service.StudentService;
import org.vasvari.gradebookweb.business.service.TeacherService;
import org.vasvari.gradebookweb.business.service.UserService;

import javax.validation.Valid;
import java.util.Optional;

import static org.vasvari.gradebookweb.business.dto.UserRole.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    @GetMapping("/users")
    public String listAllUsers(ModelMap model) {
        model.addAttribute("users", userService.findAllUsers());

        return "users";
    }

    @GetMapping("/users/students/{id}")
    public String navigateToStudentUserOrUserCreation(@PathVariable("id") Long studentId, ModelMap model) {
        Optional<UserDto> userMaybe = userService.findStudentUser(studentId);
        model.addAttribute("role", UserRole.STUDENT);
        model.addAttribute("schoolActorId", studentId);
        if (userMaybe.isEmpty()) return "redirect:/users/new";
        long userId = userMaybe.get().getId();

        return "redirect:/users/" + userId;
    }

    @GetMapping("/users/teachers/{id}")
    public String navigateToTeacherUserOrUserCreation(@PathVariable("id") Long teacherId, ModelMap model) {
        Optional<UserDto> userMaybe = userService.findTeacherUser(teacherId);
        model.addAttribute("role", UserRole.TEACHER);
        model.addAttribute("schoolActorId", teacherId);
        if (userMaybe.isEmpty()) return "redirect:/users/new";
        long userId = userMaybe.get().getId();

        return "redirect:/users/" + userId;
    }

    @GetMapping("/users/new")
    public String showUserCreateView(@RequestParam(value = "role", required = false, defaultValue = "ADMIN") UserRole role,
                                     @RequestParam(value = "schoolActorId", required = false) Long schoolActorId,
                                     @ModelAttribute UsernameInput usernameInput,
                                     ModelMap model) {
        model.addAttribute("role", role);
//        model.addAttribute("schoolActorId", schoolActorId);
        if (role.equals(UserRole.TEACHER))
            model.addAttribute("teacherDto", teacherService.findTeacherById(schoolActorId));
        if (role.equals(UserRole.STUDENT))
            model.addAttribute("studentDto", studentService.findStudentById(schoolActorId));

        return "user-create";
    }

    @PostMapping("/users/create-admin-user")
    public String createAdminUser(@Valid UsernameInput usernameInput, BindingResult bindingResult, ModelMap model) {
        model.addAttribute("role", ADMIN);
        model.addAttribute("usernameInput", usernameInput);
        if (bindingResult.hasErrors()) return "user-create";

        model.addAttribute("initialCredentials", userService.createAdminUser(usernameInput));

        return "user-create";
    }

    @PostMapping("/users/students/{studentId}")
    public String createStudentUser(@PathVariable("studentId") Long studentId, ModelMap model) {
        model.addAttribute("role", STUDENT);
        model.addAttribute("studentDto", studentService.findStudentById(studentId));
        model.addAttribute("initialCredentials", userService.createStudentUser(studentId));

        return "user-create";
    }

    @PostMapping("/users/teachers/{teacherId}")
    public String createTeacherUser(@PathVariable("teacherId") Long teacherId, ModelMap model) {
        model.addAttribute("role", TEACHER);
        model.addAttribute("teacherDto", teacherService.findTeacherById(teacherId));
        model.addAttribute("initialCredentials", userService.createTeacherUser(teacherId));

        return "user-create";
    }

    @GetMapping("/users/{id}")
    public String showUserDetails(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("editing", true);
        model.addAttribute("roleOptions", UserRole.values());
        model.addAttribute("userDto", userService.findUserById(id));

        return "user-details";
    }

    @PostMapping(value = "/users/{id}", params = "disable")
    public String disableUser(@PathVariable("id") Long userId, ModelMap model) {
        userService.disableUser(userId);
        model.addAttribute("editing", true);
        model.addAttribute("roleOptions", UserRole.values());
        model.addAttribute("userDto", userService.findUserById(userId));

        return "user-details";
    }

    @PostMapping(value = "/users/{id}", params = "enable")
    public String enableUser(@PathVariable("id") Long userId, ModelMap model) {
        userService.enableUser(userId);
        model.addAttribute("editing", true);
        model.addAttribute("roleOptions", UserRole.values());
        model.addAttribute("userDto", userService.findUserById(userId));

        return "user-details";
    }

    @RequestMapping(value = "/users/{id}", params = "delete")
    public String deleteUser(@PathVariable("id") Long userId, ModelMap model) {
        userService.deleteUser(userId);
        model.addAttribute("users", userService.findAllUsers());

        return "users";
    }

}
