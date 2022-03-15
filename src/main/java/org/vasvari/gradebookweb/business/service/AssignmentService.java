package org.vasvari.gradebookweb.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vasvari.gradebookweb.business.dto.AssignmentInput;
import org.vasvari.gradebookweb.business.dto.AssignmentOutput;
import org.vasvari.gradebookweb.business.service.gateway.AssignmentGateway;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentGateway gateway;

    public AssignmentOutput findAssignmentById(Long id) {
        return gateway.findAssignmentById(id);
    }

    public List<AssignmentOutput> findAllAssignments() {
        return new ArrayList<>(gateway.findAllAssignments());
    }

    public void save(AssignmentInput assignment) {
        gateway.save(assignment);
    }

    public void updateAssignment(Long id, AssignmentInput update) {
        gateway.updateAssignment(id, update);
    }

    public void deleteAssignment(Long id) {
        gateway.deleteAssignment(id);
    }
}
