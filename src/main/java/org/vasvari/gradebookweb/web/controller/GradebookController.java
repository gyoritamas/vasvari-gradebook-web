package org.vasvari.gradebookweb.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.vasvari.gradebookweb.business.dto.GradebookInput;
import org.vasvari.gradebookweb.business.dto.GradebookOutput;
import org.vasvari.gradebookweb.business.dto.mapper.GradebookEntryMapper;
import org.vasvari.gradebookweb.business.service.AssignmentService;
import org.vasvari.gradebookweb.business.service.SubjectService;
import org.vasvari.gradebookweb.business.service.GradebookService;
import org.vasvari.gradebookweb.business.service.StudentService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class GradebookController {
    private final GradebookService gradebookService;
    private final StudentService studentService;
    private final SubjectService subjectService;
    private final AssignmentService assignmentService;
    private final GradebookEntryMapper mapper;

    @GetMapping("/gradebook-entries")
    public String findAllGradebookEntries(Model model) {
        model.addAttribute("entries", gradebookService.findGradebookEntriesForUser());

        return "gradebook-entries";
    }

    @GetMapping("/student_gradebook/{student}")
    public String findGradebookEntriesOfStudent(@PathVariable("student") Long studentId, Model model) {
        model.addAttribute("entries", gradebookService.findGradebookEntriesOfStudent(studentId));

        return "gradebook-entries";
    }

    @GetMapping("/subject_gradebook/{subject}")
    public String findGradebookEntriesOfSubject(@PathVariable("subject") Long subjectId, Model model) {
        model.addAttribute("entries", gradebookService.findGradebookEntriesOfSubject(subjectId));

        return "gradebook-entries";
    }

    @GetMapping("gradebook-entries/new")
    public String showEmptyForm(@ModelAttribute GradebookInput gradebookInput, ModelMap model) {
        model.addAttribute("studentList", studentService.findAllStudents());
        model.addAttribute("subjectList", subjectService.findSubjectsForUser());
        model.addAttribute("assignmentList", assignmentService.findAllAssignments());

        return "gradebook-entry";
    }

    @GetMapping("/gradebook-entries/{id}")
    public String showFormWithEntry(@PathVariable("id") Long entryId, ModelMap model) {
        GradebookOutput gradebookOutput = gradebookService.findGradebookEntryById(entryId);
        GradebookInput gradebookInput = mapper.map(gradebookOutput);
        model.addAttribute("gradebookInput", gradebookInput);
        model.addAttribute("studentList", studentService.findAllStudents());
        model.addAttribute("subjectList", subjectService.findSubjectsForUser());
        model.addAttribute("assignmentList", assignmentService.findAllAssignments());
        model.addAttribute("editing", true);

        return "gradebook-entry";
    }

    @PostMapping(value = "gradebook-entries/new")
    public String saveGradebookEntry(@Valid GradebookInput gradebookInput, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) return "gradebook-entry";
        gradebookService.saveGradebookEntry(gradebookInput);
        model.clear();

        return "redirect:/gradebook-entries";
    }

    @RequestMapping("/gradebook-entries/{id}")
    public String updateGradebookEntry(@PathVariable("id") Long id,
                                       @Valid GradebookInput update,
                                       BindingResult bindingResult,
                                       ModelMap model) {
        model.addAttribute("editing", true);
        model.addAttribute("studentList", studentService.findAllStudents());
        model.addAttribute("subjectList", subjectService.findSubjectsForUser());
        model.addAttribute("assignmentList", assignmentService.findAllAssignments());
        if (bindingResult.hasErrors()) return "gradebook-entry";
        gradebookService.updateEntry(id, update);
        model.clear();

        return "redirect:/gradebook-entries";
    }

    @RequestMapping(value = "/gradebook-entries/{id}", params = "delete")
    public String deleteEntry(@PathVariable("id") Long id) {
        gradebookService.deleteGradebookEntry(id);

        return "redirect:/gradebook-entries";
    }

}
