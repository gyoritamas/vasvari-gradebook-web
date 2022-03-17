package org.vasvari.gradebookweb.business.dto.mapper;

import org.springframework.stereotype.Component;
import org.vasvari.gradebookweb.business.dto.CourseInput;
import org.vasvari.gradebookweb.business.dto.CourseOutput;

@Component
public class CourseMapper {
    public CourseInput map(CourseOutput courseOutput) {
        return CourseInput.builder()
                .name(courseOutput.getName())
                .teacherId(courseOutput.getTeacher().getId())
                .build();
    }
}
