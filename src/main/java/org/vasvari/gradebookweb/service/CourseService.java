package org.vasvari.gradebookweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vasvari.gradebookweb.dto.CourseInput;
import org.vasvari.gradebookweb.dto.CourseOutput;
import org.vasvari.gradebookweb.service.gateway.CourseGateway;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseGateway gateway;

    public CourseOutput findCourseById(Long id) {
        return gateway.findClassById(id);
    }

    public List<CourseOutput> findAllCourses() {
        return new ArrayList<>(gateway.findCourses());
    }

    public void save(CourseInput courseInput) {
        gateway.save(courseInput);
    }

    public void updateCourse(Long id, CourseInput update) {
        gateway.updateClass(id, update);
    }

    public void deleteCourse(Long id) {
        gateway.deleteClass(id);
    }
}
