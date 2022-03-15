package org.vasvari.gradebookweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.vasvari.gradebookweb.dto.StudentDto;
import org.vasvari.gradebookweb.jwt.TokenRepository;
import org.vasvari.gradebookweb.service.StudentService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    @GetMapping
    public String listAllStudents(Model model) {
        model.addAttribute("students", service.findAllStudents());

        return "students";
    }

    @GetMapping("/new")
    public String showEmptyForm(StudentDto studentDto) {
        return "student";
    }

    @GetMapping("/{id}")
    public String showFromWithStudent(@PathVariable("id") Long id, Model model) {
        model.addAttribute("editing", true);
        model.addAttribute("studentDto", service.findStudentById(id));

        return "student";
    }

    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveStudent(@Valid StudentDto student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "student";
        service.save(student);

        return "redirect:/students";
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateStudent(@PathVariable("id") Long id,
                                @Valid StudentDto student,
                                BindingResult bindingResult,
                                Model model) {
        model.addAttribute("editing", true);
        if (bindingResult.hasErrors()) return "student";
        service.updateStudent(id, student);

        return "redirect:/students";
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable("id") Long id) {
        service.deleteStudent(id);

        return "redirect:/students";
    }
}
