package org.vasvari.gradebookweb.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.vasvari.gradebookweb.business.dto.SubjectInput;
import org.vasvari.gradebookweb.business.dto.SubjectOutput;
import org.vasvari.gradebookweb.business.dto.mapper.SubjectMapper;
import org.vasvari.gradebookweb.business.service.StudentService;
import org.vasvari.gradebookweb.business.service.SubjectService;
import org.vasvari.gradebookweb.business.service.TeacherService;
import org.vasvari.gradebookweb.business.util.UserUtil;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class SubjectController implements WebMvcConfigurer {
    private final UserUtil userUtil;
    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final SubjectMapper mapper;

    @GetMapping("/subjects")
    public String listAllSubjects(ModelMap model) {
        model.addAttribute("userRole", userUtil.userRole().name());
        model.addAttribute("subjects", subjectService.findSubjectsForUser());

        return "subjects";
    }

    @GetMapping("/subjects/new")
    public String showEmptyForm(@ModelAttribute SubjectInput subjectInput, ModelMap model) {
        model.addAttribute("teacherOptions", teacherService.findAllTeachers());

        return "subject";
    }

    @GetMapping("/subjects/{id}")
    public String showFormWithSubject(@PathVariable("id") Long subjectId, ModelMap model) {
        SubjectOutput subjectOutput = subjectService.findSubjectById(subjectId);
        SubjectInput subjectInput = mapper.map(subjectOutput);
        model.addAttribute("editing", true);
        model.addAttribute("subjectInput", subjectInput);
        model.addAttribute("teacherOptions", teacherService.findAllTeachers());
        model.addAttribute("studentOptions", studentService.findAllStudents());
        model.addAttribute("studentsOfCourse", subjectService.findStudentsOfSubject(subjectId));

        return "subject";
    }

    @PostMapping("/subjects/new")
    public String saveSubject(@Valid SubjectInput subjectInput,
                              BindingResult bindingResult,
                              ModelMap model) {
        model.addAttribute("teacherOptions", teacherService.findAllTeachers());
        if (bindingResult.hasErrors()) return "subject";

        subjectService.saveSubject(subjectInput);
        model.clear();

        return "redirect:/subjects";
    }

    @RequestMapping("/subjects/{id}")
    public String updateSubject(@PathVariable("id") Long subjectId,
                                @Valid SubjectInput update,
                                BindingResult bindingResult,
                                ModelMap model) {
        model.addAttribute("editing", true);
        model.addAttribute("teacherOptions", teacherService.findAllTeachers());
        model.addAttribute("studentOptions", studentService.findAllStudents());
        model.addAttribute("studentsOfCourse", subjectService.findStudentsOfSubject(subjectId));
        if (bindingResult.hasErrors()) return "subject";

        subjectService.updateSubject(subjectId, update);
        model.clear();

        return "redirect:/subjects";
    }

    @RequestMapping("/subjects/{subjectId}/add-student")
    public String addStudentToSubject(@PathVariable("subjectId") Long subjectId,
                                      @RequestParam("studentId") Long studentId,
                                      ModelMap model) {
        model.addAttribute("editing", true);
        model.addAttribute("studentOptions", studentService.findAllStudents());

        subjectService.addStudentToSubject(subjectId, studentId);
        model.clear();

        return "redirect:/subjects/" + subjectId;
    }

    @RequestMapping(value = "/subjects/{id}", params = "delete")
    public String deleteSubject(@PathVariable("id") Long id) {
        subjectService.deleteSubject(id);

        return "redirect:/subjects";
    }
}