package org.vasvari.gradebookweb.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.vasvari.gradebookweb.business.dto.StudentDto;
import org.vasvari.gradebookweb.business.service.StudentService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;

    @GetMapping("/students")
    public String listAllStudents(Model model) {
        model.addAttribute("students", service.findAllStudentsForUser());

        return "students";
    }

    @GetMapping("/students/new")
    public String showEmptyForm(@ModelAttribute StudentDto studentDto) {
        return "student";
    }

    @GetMapping("/students/{id}")
    public String showFormWithStudent(@PathVariable("id") Long id, Model model) {
        model.addAttribute("studentDto", service.findStudentById(id));
        model.addAttribute("editing", true);

        return "student";
    }

    @PostMapping(value = "/students/new")
    public String saveStudent(@Valid StudentDto student, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) return "student";
        service.saveStudent(student);
        model.clear();

        return "redirect:/students";
    }

    @RequestMapping(value = "/students/{id}")
    public String updateStudent(@PathVariable("id") Long id,
                                @Valid StudentDto student,
                                BindingResult bindingResult,
                                ModelMap model) {
        model.addAttribute("editing", true);
        if (bindingResult.hasErrors()) return "student";
        service.updateStudent(id, student);
        model.clear();

        return "redirect:/students";
    }

    @RequestMapping(value = "/students/{id}", params = "delete")
    public String deleteStudent(@PathVariable("id") Long id) {
        service.deleteStudent(id);

        return "redirect:/students";
    }
}
