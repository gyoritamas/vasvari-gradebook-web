package org.vasvari.gradebookweb.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.vasvari.gradebookweb.business.dto.CourseInput;
import org.vasvari.gradebookweb.business.dto.CourseOutput;
import org.vasvari.gradebookweb.business.service.CourseService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;

    @ModelAttribute("allCourses")
    public List<CourseOutput> populateCourses() {
        return service.findAllCourses();
    }

    @GetMapping("/courses")
    public String showAllCourses(CourseInput courseInput) {
        return "courses";
    }

    @RequestMapping(value = "/courses")
    public String saveCourse(@Valid CourseInput courseInput,
                             BindingResult bindingResult,
                             ModelMap model) {
        if (bindingResult.hasErrors()) return "courses";
        service.save(courseInput);
        model.clear();

        return "redirect:/courses";
    }

    @PostMapping(value = "/courses/{id}", params = {"edit"})
    public String useEditButton(@PathVariable("id") Long id, ModelMap model) {

        CourseOutput courseOutput = service.findCourseById(id);
        CourseInput courseInput = new CourseInput();
        courseInput.setName(courseOutput.getName());
        model.addAttribute("courseInput", courseInput);

        return "courses";
    }

    @GetMapping(value = "/courses/{id}")
    public String editCourseBy(@PathVariable("id") Long id, ModelMap model) {

        CourseOutput courseOutput = service.findCourseById(id);
        CourseInput courseInput = new CourseInput();
        courseInput.setName(courseOutput.getName());
        model.addAttribute("courseInput", courseInput);

        return "courses";
    }

    @RequestMapping(value = "courses/{id}")
    public String updateCourse(@PathVariable("id") Long id,
                               @Valid CourseInput courseInput,
                               BindingResult bindingResult,
                               ModelMap model) {
        if (bindingResult.hasErrors()) return "courses";
        service.updateCourse(id, courseInput);
        model.clear();

        return "redirect:/courses";
    }

    @RequestMapping(value = "courses/{id}", params = {"delete"})
    public String deleteCourse(@PathVariable("id") Long id) {
        service.deleteCourse(id);

        return "redirect:/courses";
    }

}
