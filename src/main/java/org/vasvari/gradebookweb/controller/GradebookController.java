package org.vasvari.gradebookweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.vasvari.gradebookweb.dto.GradebookInput;
import org.vasvari.gradebookweb.service.AssignmentService;
import org.vasvari.gradebookweb.service.ClassService;
import org.vasvari.gradebookweb.service.GradebookService;
import org.vasvari.gradebookweb.service.StudentService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class GradebookController {
    private final GradebookService gradebookService;
    private final StudentService studentService;
    private final ClassService classService;
    private final AssignmentService assignmentService;

    @GetMapping("/gradebook-entries")
    public String findAllGradebookEntries(Model model) {
        model.addAttribute("entries", gradebookService.findAllGradebookEntries());

        return "gradebook-entries";
    }

    @GetMapping("/student_gradebook/{student}")
    public String findGradebookEntriesOfStudent(@PathVariable("student") Long studentId, Model model) {
        model.addAttribute("entries", gradebookService.findGradebookEntriesOfStudent(studentId));

        return "gradebook-entries";
    }

    @GetMapping("/class_gradebook/{class}")
    public String findGradebookEntriesOfClass(@PathVariable("class") Long classId, Model model) {
        model.addAttribute("entries", gradebookService.findGradebookEntriesOfClass(classId));

        return "gradebook-entries";
    }

    @GetMapping("gradebook-entries/new")
    public String showEmptyForm(GradebookInput gradebookInput, Model model) {
        model.addAttribute("studentList", studentService.findAllStudents());
        model.addAttribute("classList", classService.findAllClasses());
        model.addAttribute("assignmentList", assignmentService.findAllAssignments());

        return "gradebook-entry";
    }

    @PostMapping(value = "gradebook-entries/new", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveGradebookEntry(@Valid GradebookInput gradebookInput, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) return "gradebook-entry";
        gradebookService.save(gradebookInput);

        return "redirect:/gradebook-entries";
    }

}
