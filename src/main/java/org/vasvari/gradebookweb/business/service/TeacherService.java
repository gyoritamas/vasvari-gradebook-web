package org.vasvari.gradebookweb.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vasvari.gradebookweb.business.dto.TeacherDto;
import org.vasvari.gradebookweb.business.service.gateway.TeacherGateway;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherGateway gateway;

    public TeacherDto findTeacherById(Long id) {
        return gateway.findTeacherById(id);
    }

    public List<TeacherDto> findAllTeachers() {
        return new ArrayList<>(gateway.findAllTeachers());
    }

    public void saveTeacher(TeacherDto teacher) {
        gateway.save(teacher);
    }

    public void updateTeacher(Long id, TeacherDto teacher) {
        gateway.updateTeacher(id, teacher);
    }

    public void deleteTeacher(Long id) {
        gateway.deleteTeacher(id);
    }
}
