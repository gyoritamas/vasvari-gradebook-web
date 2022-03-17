package org.vasvari.gradebookweb.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.vasvari.gradebookweb.business.dto.CourseInput;
import org.vasvari.gradebookweb.business.service.CourseService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CourseController implements WebMvcConfigurer {
    private final CourseService service;

    @GetMapping("/courses")
    public String listAllCourses(Model model) {
        model.addAttribute("courses", service.findAllCourses());

        return "courses";
    }

    @GetMapping("/courses/new")
    public String showEmptyForm(@ModelAttribute CourseInput courseInput) {
        return "course";
    }

    @GetMapping("/courses/{id}")
    public String showFormWithCourse(@PathVariable("id") Long id, Model model) {
        model.addAttribute("editing", true);
        model.addAttribute("courseInput", service.findCourseById(id));
        return "course";
    }

    @PostMapping("/courses/new")
    public String saveCourse(@Valid CourseInput courseInput, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) return "course";
        service.save(courseInput);
        model.clear();

        return "redirect:/courses";
    }

    @RequestMapping("/courses/{id}")
    public String updateCourse(@PathVariable("id") Long id,
                               @Valid CourseInput update,
                               BindingResult bindingResult,
                               ModelMap model) {
        model.addAttribute("editing", true);
        if (bindingResult.hasErrors()) return "course";
        service.updateCourse(id, update);
        model.clear();

        return "redirect:/courses";
    }

    @RequestMapping(value = "/courses/{id}", params = "delete")
    public String deleteCourse(@PathVariable("id") Long id) {
        service.deleteCourse(id);

        return "redirect:/courses";
    }
}