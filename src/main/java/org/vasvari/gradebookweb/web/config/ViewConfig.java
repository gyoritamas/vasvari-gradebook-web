package org.vasvari.gradebookweb.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ViewConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/courses").setViewName("courses");
        registry.addViewController("/students").setViewName("students");
        registry.addViewController("/assignments").setViewName("assignments");
        registry.addViewController("/gradebook-entries").setViewName("gradebook-entries");
        registry.addViewController("/error").setViewName("error");

    }
}
