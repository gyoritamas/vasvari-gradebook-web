package org.vasvari.gradebookweb.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.vasvari.gradebookweb.business.dto.TeacherDto;
import org.vasvari.gradebookweb.business.service.TeacherService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService service;

    @GetMapping("/teachers")
    public String listAllTeachers(Model model) {
        model.addAttribute("teachers", service.findAllTeachers());

        return "teachers";
    }

    @GetMapping("/teachers/new")
    public String showEmptyForm(@ModelAttribute TeacherDto teacherDto) {
        return "teacher";
    }

    @GetMapping("/teachers/{id}")
    public String showFormWithTeacher(@PathVariable("id") Long id, Model model) {
        model.addAttribute("editing", true);
        model.addAttribute("teacherDto", service.findTeacherById(id));

        return "teacher";
    }

    @PostMapping(value = "/teachers/new")
    public String saveTeacher(@Valid TeacherDto teacher, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) return "teacher";
        service.saveTeacher(teacher);
        model.clear();

        return "redirect:/teachers";
    }

    @RequestMapping(value = "/teachers/{id}")
    public String updateTeacher(@PathVariable("id") Long id,
                                @Valid TeacherDto teacher,
                                BindingResult bindingResult,
                                ModelMap model) {
        model.addAttribute("editing", true);
        if (bindingResult.hasErrors()) return "teacher";

        service.updateTeacher(id, teacher);
        model.clear();

        return "redirect:/teachers";
    }

    @RequestMapping(value = "/teachers/{id}", params = "delete")
    public String deleteTeacher(@PathVariable("id") Long id) {
        service.deleteTeacher(id);

        return "redirect:/teachers";
    }
}
