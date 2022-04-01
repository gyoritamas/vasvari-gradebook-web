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
import org.vasvari.gradebookweb.business.model.request.AssignmentRequest;
import org.vasvari.gradebookweb.business.model.request.GradebookRequest;
import org.vasvari.gradebookweb.business.model.request.StudentRequest;
import org.vasvari.gradebookweb.business.service.AssignmentService;
import org.vasvari.gradebookweb.business.service.GradebookService;
import org.vasvari.gradebookweb.business.service.StudentService;
import org.vasvari.gradebookweb.business.service.SubjectService;
import org.vasvari.gradebookweb.business.util.UserUtil;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class GradebookController {
    private final GradebookService gradebookService;
    private final StudentService studentService;
    private final SubjectService subjectService;
    private final AssignmentService assignmentService;
    private final GradebookEntryMapper mapper;
    private final UserUtil userUtil;

    @GetMapping("/gradebook-entries")
    public String listGradebookEntries(
            @RequestParam(value = "studentId", required = false) Long studentId,
            @RequestParam(value = "subjectId", required = false) Long subjectId,
            @RequestParam(value = "assignmentId", required = false) Long assignmentId,
            Model model) {

        if (userUtil.hasAnyRole("ADMIN", "TEACHER")) {
            model.addAttribute("studentOptions", studentService.findStudentsForUser());
        }
        model.addAttribute("subjectOptions", subjectService.findSubjectsForUser());
        model.addAttribute("assignmentOptions", assignmentService.findAssignmentsForUser());

        // "remember" filters
        model.addAttribute("studentId", studentId);
        model.addAttribute("subjectId", subjectId);
        model.addAttribute("assignmentId", assignmentId);

        GradebookRequest request = new GradebookRequest(studentId, subjectId, assignmentId);

        model.addAttribute("entries", gradebookService.findGradebookEntriesForUser(request));

        return "gradebook-entries";
    }

    @PostMapping("/gradebook-entries/reset-filter")
    public String resetFilter(ModelMap model) {
        model.clear();

        return "redirect:/gradebook-entries";
    }

    @GetMapping(value = "gradebook-entries/new")
    public String showEntryCreateForm(@RequestParam(value = "subjectId", required = false) Long subjectId,
                                              @ModelAttribute GradebookInput gradebookInput,
                                              ModelMap model) {
        model.addAttribute("subjectOptions", subjectService.findSubjectsForUser());
        if (subjectId != null) {
            model.addAttribute("selectedSubject", subjectId);

            StudentRequest studentRequest = new StudentRequest();
            studentRequest.setSubjectId(subjectId);
            model.addAttribute("studentOptions", studentService.findStudentsForUser(studentRequest));

            AssignmentRequest assignmentRequest = new AssignmentRequest();
            assignmentRequest.setSubjectId(subjectId);
            model.addAttribute("assignmentOptions", assignmentService.findAssignmentsForUser(assignmentRequest));
        }

        return "entry-create";
    }

    @GetMapping("/gradebook-entries/{id}")
    public String showFormWithEntry(@PathVariable("id") Long entryId, ModelMap model) {
        GradebookOutput gradebookOutput = gradebookService.findGradebookEntryById(entryId);
        GradebookInput gradebookInput = mapper.map(gradebookOutput);
        model.addAttribute("gradebookInput", gradebookInput);
        model.addAttribute("studentList", studentService.findStudentsForUser());
        model.addAttribute("subjectList", subjectService.findSubjectsForUser());
        model.addAttribute("assignmentList", assignmentService.findAssignmentsForUser());
        model.addAttribute("editing", true);

        return "entry-details";
    }

    @PostMapping(value = "gradebook-entries/new")
    public String saveGradebookEntry(@Valid GradebookInput gradebookInput, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) return "gradebook-entry";
        gradebookService.saveGradebookEntry(gradebookInput);
        model.clear();

        return "redirect:/gradebook-entries";
    }

//    @RequestMapping("/gradebook-entries/{id}")
//    public String updateGradebookEntry(@PathVariable("id") Long id,
//                                       @Valid GradebookInput update,
//                                       BindingResult bindingResult,
//                                       ModelMap model) {
//        model.addAttribute("editing", true);
//        model.addAttribute("studentList", studentService.findStudentsForUser());
//        model.addAttribute("subjectList", subjectService.findSubjectsForUser());
//        model.addAttribute("assignmentList", assignmentService.findAssignmentsForUser());
//        if (bindingResult.hasErrors()) return "gradebook-entry";
//        gradebookService.updateEntry(id, update);
//        model.clear();
//
//        return "redirect:/gradebook-entries";
//    }

    @RequestMapping(value = "/gradebook-entries/{id}", params = "delete")
    public String deleteEntry(@PathVariable("id") Long id) {
        gradebookService.deleteGradebookEntry(id);

        return "redirect:/gradebook-entries";
    }

}
