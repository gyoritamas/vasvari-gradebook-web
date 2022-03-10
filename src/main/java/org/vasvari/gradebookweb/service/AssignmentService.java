package org.vasvari.gradebookweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vasvari.gradebookweb.dto.AssignmentInput;
import org.vasvari.gradebookweb.dto.AssignmentOutput;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssignmentService {
    @Autowired
    private AssignmentGateway gateway;

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
