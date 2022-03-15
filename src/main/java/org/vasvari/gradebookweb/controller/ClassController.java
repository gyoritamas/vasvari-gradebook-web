package org.vasvari.gradebookweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.vasvari.gradebookweb.dto.ClassInput;
import org.vasvari.gradebookweb.service.ClassService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/classes")
public class ClassController implements WebMvcConfigurer {

    private final ClassService service;

    @GetMapping
    public String listAllClasses(Model model) {
        model.addAttribute("classes", service.findAllClasses());

        return "classes";
    }

    @GetMapping("/new")
    public String showEmptyForm(ClassInput classInput) {
        return "class";
    }

    @GetMapping("/{id}")
    public String showFormWithClass(@PathVariable("id") Long id, Model model) {
        model.addAttribute("editing", true);
        model.addAttribute("classInput", service.findClassById(id));

        return "class";
    }

    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveClass(@Valid ClassInput clazz, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "class";
        service.save(clazz);

        return "redirect:/classes";
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateClass(@PathVariable("id") Long id,
                              @Valid ClassInput update,
                              BindingResult bindingResult,
                              Model model) {
        model.addAttribute("editing", true);
        if (bindingResult.hasErrors()) return "class";
        service.updateClass(id, update);

        return "redirect:/classes";
    }

    @DeleteMapping("/{id}")
    public String deleteClass(@PathVariable("id") Long id) {
        service.deleteClass(id);

        return "redirect:/classes";
    }

}
