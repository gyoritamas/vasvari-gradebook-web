package org.vasvari.gradebookweb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vasvari.gradebookweb.dto.ClassInput;
import org.vasvari.gradebookweb.dto.ClassOutput;
import org.vasvari.gradebookweb.model.ClassOutputModel;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ClassService {

    @Autowired
    private ClassGateway gateway;

    public ClassOutput findClassById(Long id) {
        return gateway.findClassById(id).getBody().getContent();
    }

    public List<ClassOutput> findAllClasses() {
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
