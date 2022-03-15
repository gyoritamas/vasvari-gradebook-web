package org.vasvari.gradebookweb.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.vasvari.gradebookweb.business.dto.AssignmentInput;
import org.vasvari.gradebookweb.business.dto.AssignmentType;
import org.vasvari.gradebookweb.business.service.AssignmentService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/assignments")
public class AssignmentController {

    private final AssignmentService service;

    @ModelAttribute("typeOptions")
    public AssignmentType[] assignmentTypes(){
        return AssignmentType.values();
    }


    @GetMapping
    public String findAllAssignments(Model model) {
        model.addAttribute("assignments", service.findAllAssignments());

        return "assignments";
    }

    @GetMapping("/new")
    public String showEmptyForm(AssignmentInput assignmentInput, Model model) {
        //model.addAttribute("typeOptions", AssignmentType.values());

        return "assignment";
    }

    @GetMapping("/{id}")
    public String showFormWithAssignment(@PathVariable("id") Long id, Model model) {
        model.addAttribute("editing", true);
        //model.addAttribute("typeOptions", AssignmentType.values());
        model.addAttribute("assignmentInput", service.findAssignmentById(id));

        return "assignment";
    }

    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveAssignment(@Valid AssignmentInput assignment, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("typeOptions", AssignmentType.values());
            return "assignment";
        }
        service.save(assignment);

        return "redirect:/assignments";
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateAssignment(@PathVariable("id") Long id,
                                   @Valid AssignmentInput update,
                                   BindingResult bindingResult,
                                   Model model) {
        model.addAttribute("editing", true);
        if (bindingResult.hasErrors()) {
            model.addAttribute("typeOptions", AssignmentType.values());
            return "assignment";
        }
        service.updateAssignment(id, update);

        return "redirect:/assignments";
    }

    @DeleteMapping("/{id}")
    public String deleteAssignment(@PathVariable("id") Long id) {
        service.deleteAssignment(id);

        return "redirect:/assignments";
    }
}
