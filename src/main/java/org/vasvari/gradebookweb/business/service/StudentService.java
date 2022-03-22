package org.vasvari.gradebookweb.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vasvari.gradebookweb.business.dto.StudentDto;
import org.vasvari.gradebookweb.business.dto.UserRole;
import org.vasvari.gradebookweb.business.service.gateway.StudentGateway;
import org.vasvari.gradebookweb.business.util.UserUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final UserUtil userUtil;
    private final StudentGateway gateway;

    public StudentDto findStudentById(Long id) {
        return gateway.findStudentById(id);
    }

    public List<StudentDto> findAllStudentsForUser() {
        switch (userUtil.userRole()) {
            case ADMIN:
                return new ArrayList<>(gateway.findAllStudents());
            case TEACHER:
                return findStudentsOfCurrentUserAsTeacher();
            case STUDENT:
                return Collections.emptyList();
            default:
                throw new RuntimeException("Unrecognised user role");
        }
    }

    public List<StudentDto> findAllStudents() {
        return new ArrayList<>(gateway.findAllStudents());
    }

    private List<StudentDto> findStudentsOfCurrentUserAsTeacher() {
        return new ArrayList<>(gateway.findStudentsOfCurrentUserAsTeacher());
    }

    public void saveStudent(StudentDto student) {
        gateway.saveStudent(student);
    }

    public void updateStudent(Long id, StudentDto student) {
        gateway.updateStudent(id, student);
    }

    public void deleteStudent(Long id) {
        gateway.deleteStudent(id);
    }
}
