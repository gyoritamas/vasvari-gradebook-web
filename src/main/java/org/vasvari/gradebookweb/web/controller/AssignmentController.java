package org.vasvari.gradebookweb.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.vasvari.gradebookweb.business.dto.AssignmentInput;
import org.vasvari.gradebookweb.business.dto.AssignmentOutput;
import org.vasvari.gradebookweb.business.dto.AssignmentType;
import org.vasvari.gradebookweb.business.dto.SubjectOutput;
import org.vasvari.gradebookweb.business.dto.mapper.AssignmentMapper;
import org.vasvari.gradebookweb.business.service.AssignmentService;
import org.vasvari.gradebookweb.business.service.SubjectService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final SubjectService subjectService;
    private final AssignmentMapper mapper;

    @ModelAttribute("typeOptions")
    public AssignmentType[] assignmentTypes() {
        return AssignmentType.values();
    }

    @ModelAttribute("subjectOptions")
    public List<SubjectOutput> populateSubjectOptions() {
        return subjectService.findSubjectsForUser();
    }

    @GetMapping("/assignments")
    public String findAllAssignments(Model model) {
        model.addAttribute("assignments", assignmentService.findAllAssignments());

        return "assignments";
    }

    @GetMapping("/assignments/new")
    public String showEmptyForm(@ModelAttribute AssignmentInput assignmentInput) {

        return "assignment";
    }

    @GetMapping("/assignments/{id}")
    public String showFormWithAssignment(@PathVariable("id") Long id, Model model) {
        AssignmentOutput assignmentOutput = assignmentService.findAssignmentById(id);
        AssignmentInput assignmentInput = mapper.map(assignmentOutput);
        model.addAttribute("assignmentInput", assignmentInput);
        model.addAttribute("editing", true);

        return "assignment";
    }

    @PostMapping("/assignments/new")
    public String saveAssignment(@Valid AssignmentInput assignment, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) return "assignment";
        assignmentService.saveAssignment(assignment);
        model.clear();

        return "redirect:/assignments";
    }

    @RequestMapping(value = "/assignments/{id}")
    public String updateAssignment(@PathVariable("id") Long id,
                                   @Valid AssignmentInput update,
                                   BindingResult bindingResult,
                                   ModelMap model) {
        model.addAttribute("editing", true);
        if (bindingResult.hasErrors()) return "assignment";
        assignmentService.updateAssignment(id, update);
        model.clear();

        return "redirect:/assignments";
    }

    @RequestMapping(value = "/assignments/{id}", params = "delete")
    public String deleteAssignment(@PathVariable("id") Long id) {
        assignmentService.deleteAssignment(id);

        return "redirect:/assignments";
    }
}
