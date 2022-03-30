package org.vasvari.gradebookweb.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.vasvari.gradebookweb.business.dto.StudentDto;
import org.vasvari.gradebookweb.business.dto.UserRole;
import org.vasvari.gradebookweb.business.model.request.StudentRequest;
import org.vasvari.gradebookweb.business.service.StudentService;
import org.vasvari.gradebookweb.business.service.SubjectService;
import org.vasvari.gradebookweb.business.util.UserUtil;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final SubjectService subjectService;
    private final UserUtil userUtil;

    @GetMapping("/students")
    public String listAllStudents(@RequestParam(value = "studentName", required = false) String studentName,
                                  @RequestParam(value = "gradeLevel", required = false) Integer gradeLevel,
                                  @RequestParam(value = "subjectId", required = false) Long subjectId,
                                  Model model) {

        model.addAttribute("subjectOptions", subjectService.findSubjectsForUser());

        // "remember" filters
        model.addAttribute("studentName", studentName);
        model.addAttribute("gradeLevel", gradeLevel);
        model.addAttribute("subjectId", subjectId);

        StudentRequest request = new StudentRequest(studentName, gradeLevel, subjectId);
        model.addAttribute("students", studentService.findStudentsForUser(request));

        return "students";
    }

    @PostMapping("/students/reset-filter")
    public String resetFilter(ModelMap model) {
        model.clear();

        return "redirect:/students";
    }

    @GetMapping("/students/new")
    public String showEmptyForm(@ModelAttribute StudentDto studentDto) {
        if(!userUtil.userRole().equals(UserRole.ADMIN)) return "redirect:/students";

        return "student";
    }

    @GetMapping("/students/{id}")
    public String showFormWithStudent(@PathVariable("id") Long id, Model model) {
        model.addAttribute("editing", true);
        model.addAttribute("studentDto", studentService.findStudentById(id));

        return "student";
    }

    @PostMapping(value = "/students/new")
    public String saveStudent(@Valid StudentDto student, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) return "student";
        studentService.saveStudent(student);
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
        studentService.updateStudent(id, student);
        model.clear();

        return "redirect:/students";
    }

    @RequestMapping(value = "/students/{id}", params = "delete")
    public String deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);

        return "redirect:/students";
    }
}
