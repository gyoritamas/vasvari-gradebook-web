package org.vasvari.gradebookweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vasvari.gradebookweb.dto.ClassInput;
import org.vasvari.gradebookweb.dto.CourseOutput;
import org.vasvari.gradebookweb.service.gateway.ClassGateway;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassService {

    private final ClassGateway gateway;

    public CourseOutput findClassById(Long id) {
        return gateway.findClassById(id);
    }

    public List<CourseOutput> findAllClasses() {
        return new ArrayList<>(gateway.findAllClasses());
    }

    public void save(ClassInput clazz) {
        gateway.save(clazz);
    }

    public void updateClass(Long id, ClassInput update) {
        gateway.updateClass(id, update);
    }

    public void deleteClass(Long id) {
        gateway.deleteClass(id);
    }
}
