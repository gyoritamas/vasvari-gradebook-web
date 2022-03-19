package org.vasvari.gradebookweb.business.dto.mapper;

import org.springframework.stereotype.Component;
import org.vasvari.gradebookweb.business.dto.GradebookInput;
import org.vasvari.gradebookweb.business.dto.GradebookOutput;

@Component
public class GradebookEntryMapper {
    public GradebookInput map(GradebookOutput gradebookOutput) {
        return GradebookInput.builder()
                .studentId(gradebookOutput.getStudent().getId())
                .subjectId(gradebookOutput.getSubject().getId())
                .assignmentId(gradebookOutput.getAssignment().getId())
                .grade(gradebookOutput.getGrade())
                .build();
    }
}
