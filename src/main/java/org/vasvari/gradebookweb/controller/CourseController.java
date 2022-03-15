package org.vasvari.gradebookweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.vasvari.gradebookweb.dto.CourseInput;
import org.vasvari.gradebookweb.service.CourseService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController implements WebMvcConfigurer {

    private final CourseService service;

    @GetMapping
    public String listAllCourses(Model model) {
        model.addAttribute("courses", service.findAllCourses());

        return "courses";
    }

    @GetMapping("/new")
    public String showEmptyForm(CourseInput courseInput) {
        return "course";
    }

    @GetMapping("/{id}")
    public String showFormWithCourse(@PathVariable("id") Long id, Model model) {
        model.addAttribute("editing", true);
        model.addAttribute("courseInput", service.findCourseById(id));

        return "course";
    }

    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveCourse(@Valid CourseInput courseInput, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "course";
        service.save(courseInput);

        return "redirect:/courses";
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateCourse(@PathVariable("id") Long id,
                               @Valid CourseInput update,
                               BindingResult bindingResult,
                               Model model) {
        model.addAttribute("editing", true);
        if (bindingResult.hasErrors()) return "course";
        service.updateCourse(id, update);

        return "redirect:/courses";
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        service.deleteCourse(id);

        return "redirect:/courses";
    }

}
