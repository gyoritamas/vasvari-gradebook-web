package org.vasvari.gradebookweb.business.dto.mapper;

import org.springframework.stereotype.Component;
import org.vasvari.gradebookweb.business.dto.AssignmentInput;
import org.vasvari.gradebookweb.business.dto.AssignmentOutput;

@Component
public class AssignmentMapper {
    public AssignmentInput map(AssignmentOutput assignmentOutput) {
        return AssignmentInput.builder()
                .name(assignmentOutput.getName())
                .type(assignmentOutput.getType())
                .deadline(assignmentOutput.getDeadline())
                .description(assignmentOutput.getDescription())
                .subjectId(assignmentOutput.getSubject().getId())
                .build();
    }
}
