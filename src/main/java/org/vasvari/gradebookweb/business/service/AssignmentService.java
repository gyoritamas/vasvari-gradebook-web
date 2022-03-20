package org.vasvari.gradebookweb.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vasvari.gradebookweb.business.dto.AssignmentInput;
import org.vasvari.gradebookweb.business.dto.AssignmentOutput;
import org.vasvari.gradebookweb.business.service.gateway.AssignmentGateway;
import org.vasvari.gradebookweb.business.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

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

    public List<AssignmentOutput> findAllAssignments() {
        return new ArrayList<>(gateway.findAllAssignments());
    }

    public List<AssignmentOutput> findAssignmentsOfTeacherUser() {
        return new ArrayList<>(gateway.findAssignmentsOfCurrentUserAsTeacher());
    }

    public List<AssignmentOutput> findAssignmentsOfStudentUser() {
        return new ArrayList<>(gateway.findAssignmentsOfCurrentUserAsStudent());
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
