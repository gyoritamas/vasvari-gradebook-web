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
import org.vasvari.gradebookweb.business.dto.mapper.AssignmentMapper;
import org.vasvari.gradebookweb.business.service.AssignmentService;
import org.vasvari.gradebookweb.business.service.SubjectService;

import javax.validation.Valid;

/**
 * Handles 'assignment' and 'assignments' views
 */
@Controller
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final SubjectService subjectService;
    private final AssignmentMapper mapper;

    /**
     * Shows a list of assignments
     *
     * @param model contains attributes the view works with
     * @return view 'assignments' with all assignments
     */
    @GetMapping("/assignments")
    public String findAllAssignments(ModelMap model) {
        model.addAttribute("assignments", assignmentService.findAllAssignments());

        return "assignments";
    }

    /**
     * Shows an empty assignment form
     *
     * @param assignmentInput AssignmentInput object used to contain details of the assignment is to be created
     * @param model contains attributes the form works with
     * @return view 'assignment' with empty fields
     */
    @GetMapping("/assignments/new")
    public String showEmptyForm(@ModelAttribute AssignmentInput assignmentInput, ModelMap model) {
        model.addAttribute("typeOptions", AssignmentType.values());
        model.addAttribute("subjectOptions", subjectService.findSubjectsForUser());

        return "assignment";
    }

    /**
     * Shows assignment details in form
     *
     * @param id    id of the selected assignment
     * @param model contains attributes the form works with
     * @return view 'assignment' with details of the selected assignment
     */
    @GetMapping("/assignments/{id}")
    public String showFormWithAssignment(@PathVariable("id") Long id, Model model) {
        AssignmentOutput assignmentOutput = assignmentService.findAssignmentById(id);
        AssignmentInput assignmentInput = mapper.map(assignmentOutput);
        model.addAttribute("editing", true);
        model.addAttribute("assignmentInput", assignmentInput);
        model.addAttribute("typeOptions", AssignmentType.values());
        model.addAttribute("subjectOptions", subjectService.findSubjectsForUser());

        return "assignment";
    }

    /**
     * Creates a new assignment
     *
     * @param assignment    AssignmentInput object containing details the new assignment is to be created with
     * @param bindingResult BindingResult object for handling errors
     * @param model         contains attributes the form works with
     * @return view 'assignment' in case bindingResult contained any errors,
     * or view 'assignments' in case not
     */
    @PostMapping("/assignments/new")
    public String saveAssignment(@Valid AssignmentInput assignment, BindingResult bindingResult, ModelMap model) {
        model.addAttribute("typeOptions", AssignmentType.values());
        model.addAttribute("subjectOptions", subjectService.findSubjectsForUser());
        if (bindingResult.hasErrors()) return "assignment";

        assignmentService.saveAssignment(assignment);
        model.clear();

        return "redirect:/assignments";
    }

    /**
     * Updates assignment details
     *
     * @param id            id of the assignment
     * @param update        AssignmentInput object containing details the assignment is to be updated by
     * @param bindingResult BindingResult object for handling errors
     * @param model         contains attributes the form works with
     * @return view 'assignment' in case bindingResult contained any errors,
     * or view 'assignments' in case not
     */
    @RequestMapping(value = "/assignments/{id}")
    public String updateAssignment(@PathVariable("id") Long id,
                                   @Valid AssignmentInput update,
                                   BindingResult bindingResult,
                                   ModelMap model) {
        model.addAttribute("editing", true);
        model.addAttribute("typeOptions", AssignmentType.values());
        model.addAttribute("subjectOptions", subjectService.findSubjectsForUser());
        if (bindingResult.hasErrors()) return "assignment";

        assignmentService.updateAssignment(id, update);
        model.clear();

        return "redirect:/assignments";
    }

    /**
     * Deletes an assignment
     *
     * @param id id of the assignment
     * @return view 'assignments'
     */
    @RequestMapping(value = "/assignments/{id}", params = "delete")
    public String deleteAssignment(@PathVariable("id") Long id) {
        assignmentService.deleteAssignment(id);

        return "redirect:/assignments";
    }
}
