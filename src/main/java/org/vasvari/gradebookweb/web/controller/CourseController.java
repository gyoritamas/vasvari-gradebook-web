package org.vasvari.gradebookweb.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.vasvari.gradebookweb.business.dto.*;
import org.vasvari.gradebookweb.business.dto.mapper.CourseMapper;
import org.vasvari.gradebookweb.business.service.CourseService;
import org.vasvari.gradebookweb.business.service.TeacherService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CourseController implements WebMvcConfigurer {
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final CourseMapper mapper;

    @ModelAttribute("teacherOptions")
    public List<TeacherDto> populateTeacherOptions() {
        return teacherService.findAllTeachers();
    }

    @GetMapping("/courses")
    public String listAllCourses(Model model) {
        model.addAttribute("courses", courseService.findAllCourses());

        return "courses";
    }

    @GetMapping("/courses/new")
    public String showEmptyForm(@ModelAttribute CourseInput courseInput) {
        return "course";
    }

    @GetMapping("/courses/{id}")
    public String showFormWithCourse(@PathVariable("id") Long id, Model model) {
        CourseOutput courseOutput = courseService.findCourseById(id);
        CourseInput courseInput = mapper.map(courseOutput);
        model.addAttribute("courseInput", courseInput);
        model.addAttribute("editing", true);

        return "course";
    }

    @PostMapping("/courses/new")
    public String saveCourse(@Valid CourseInput courseInput,
                             BindingResult bindingResult,
                             ModelMap model) {
        if (bindingResult.hasErrors()) return "course";
        courseService.save(courseInput);
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
        courseService.updateCourse(id, update);
        model.clear();

        return "redirect:/courses";
    }

    @RequestMapping(value = "/courses/{id}", params = "delete")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteCourse(id);

        return "redirect:/courses";
    }
}