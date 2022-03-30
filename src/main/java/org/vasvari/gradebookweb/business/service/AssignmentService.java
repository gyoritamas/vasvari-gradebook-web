package org.vasvari.gradebookweb.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vasvari.gradebookweb.business.dto.AssignmentInput;
import org.vasvari.gradebookweb.business.dto.AssignmentOutput;
import org.vasvari.gradebookweb.business.model.request.AssignmentRequest;
import org.vasvari.gradebookweb.business.service.gateway.AssignmentGateway;
import org.vasvari.gradebookweb.business.util.UserUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentGateway gateway;
    private final UserUtil userUtil;

    public AssignmentOutput findAssignmentById(Long id) {
        return gateway.findAssignmentById(id);
    }

    public List<AssignmentOutput> findAssignmentsForUser() {
        switch (userUtil.userRole()) {
            case ADMIN:
                return findAllAssignments();
            case TEACHER:
                return findAssignmentsOfTeacherUser();
            case STUDENT:
                return findAssignmentsOfStudentUser();
            default:
                throw new RuntimeException("Unrecognised user role");
        }
    }

    public List<AssignmentOutput> findAssignmentsForUser(AssignmentRequest request) {
        return findAssignmentsForUserIncludeExpired(request).stream()
                .filter(assignment -> assignment.isExpired().equals(false))
                .collect(Collectors.toList());
    }

    public List<AssignmentOutput> findAssignmentsForUserIncludeExpired(AssignmentRequest request) {
        switch (userUtil.userRole()) {
            case ADMIN:
                return searchAssignments(request);
            case TEACHER:
                return findAssignmentsOfTeacherUser(request);
            case STUDENT:
                return findAssignmentsOfStudentUser(request);
            default:
                throw new RuntimeException("Unrecognised user role");
        }
    }

    public List<AssignmentOutput> findAllAssignments() {
        return new ArrayList<>(gateway.findAllAssignments());
    }

    public List<AssignmentOutput> searchAssignments(AssignmentRequest request) {
        return new ArrayList<>(gateway.searchAssignments(request));
    }

    public List<AssignmentOutput> findAssignmentsOfTeacherUser() {
        return new ArrayList<>(gateway.findAssignmentsOfCurrentUserAsTeacher());
    }

    public List<AssignmentOutput> findAssignmentsOfTeacherUser(AssignmentRequest request) {
        return new ArrayList<>(gateway.findAssignmentsOfCurrentUserAsTeacher(request));
    }

    public List<AssignmentOutput> findAssignmentsOfStudentUser() {
        return new ArrayList<>(gateway.findAssignmentsOfCurrentUserAsStudent());
    }

    public List<AssignmentOutput> findAssignmentsOfStudentUser(AssignmentRequest request) {
        return new ArrayList<>(gateway.findAssignmentsOfCurrentUserAsStudent(request));
    }

    public void saveAssignment(AssignmentInput assignment) {
        gateway.save(assignment);
    }

    public void updateAssignment(Long id, AssignmentInput update) {
        gateway.updateAssignment(id, update);
    }

    public void deleteAssignment(Long id) {
        gateway.deleteAssignment(id);
    }
}
