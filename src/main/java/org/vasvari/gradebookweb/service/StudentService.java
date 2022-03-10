package org.vasvari.gradebookweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vasvari.gradebookweb.dto.StudentDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentGateway gateway;

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
