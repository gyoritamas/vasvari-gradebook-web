package org.vasvari.gradebookweb.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.vasvari.gradebookweb.business.dto.SubjectInput;
import org.vasvari.gradebookweb.business.dto.SubjectOutput;
import org.vasvari.gradebookweb.business.dto.mapper.SubjectMapper;
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
    private final SubjectMapper mapper;

    @ModelAttribute("userRole")
    public String populateUserRole() {
        return userUtil.userRole().name();
    }

    @GetMapping("/subjects")
    public String listAllSubjects(Model model) {
        model.addAttribute("subjects", subjectService.findSubjectsForUser());

        return "subjects";
    }

    @GetMapping("/subjects/new")
    public String showEmptyForm(@ModelAttribute SubjectInput subjectInput, ModelMap model) {
        model.addAttribute("teacherOptions", teacherService.findAllTeachers());

        return "subject";
    }

    @GetMapping("/subjects/{id}")
    public String showFormWithCourse(@PathVariable("id") Long subjectId, Model model) {
        SubjectOutput subjectOutput = subjectService.findSubjectById(subjectId);
        SubjectInput subjectInput = mapper.map(subjectOutput);
        model.addAttribute("subjectInput", subjectInput);
        model.addAttribute("teacherOptions", teacherService.findAllTeachers());
        model.addAttribute("studentsOfCourse", subjectService.findStudentsOfSubject(subjectId));
        model.addAttribute("editing", true);

        return "subject";
    }

    @PostMapping("/subjects/new")
    public String saveSubject(@Valid SubjectInput subjectInput,
                              BindingResult bindingResult,
                              ModelMap model) {
        if (bindingResult.hasErrors()) return "subject";
        subjectService.saveSubject(subjectInput);
        model.clear();

        return "redirect:/subjects";
    }

    @RequestMapping("/subjects/{id}")
    public String updateSubject(@PathVariable("id") Long id,
                                @Valid SubjectInput update,
                                BindingResult bindingResult,
                                ModelMap model) {
        model.addAttribute("editing", true);
        model.addAttribute("teacherOptions", teacherService.findAllTeachers());
        if (bindingResult.hasErrors()) return "subject";
        subjectService.updateSubject(id, update);
        model.clear();

        return "redirect:/subjects";
    }

    @RequestMapping(value = "/subjects/{id}", params = "delete")
    public String deleteSubject(@PathVariable("id") Long id) {
        subjectService.deleteSubject(id);

        return "redirect:/subjects";
    }
}