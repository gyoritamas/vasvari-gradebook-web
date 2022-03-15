package org.vasvari.gradebookweb.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vasvari.gradebookweb.business.dto.StudentDto;
import org.vasvari.gradebookweb.business.service.gateway.StudentGateway;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentGateway gateway;

    public StudentDto findStudentById(Long id) {
        return gateway.findStudentById(id);
    }

    public List<StudentDto> findAllStudents() {
        return new ArrayList<>(gateway.findAllStudents());
    }

    public void save(StudentDto student) {
        gateway.save(student);
    }

    public void updateStudent(Long id, StudentDto student) {
        gateway.updateStudent(id, student);
    }

    public void deleteStudent(Long id) {
        gateway.deleteStudent(id);
    }
}
