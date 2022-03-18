package org.vasvari.gradebookweb.business.dto.mapper;

import org.springframework.stereotype.Component;
import org.vasvari.gradebookweb.business.dto.SubjectInput;
import org.vasvari.gradebookweb.business.dto.SubjectOutput;

@Component
public class SubjectMapper {
    public SubjectInput map(SubjectOutput subjectOutput) {
        return SubjectInput.builder()
                .name(subjectOutput.getName())
                .teacherId(subjectOutput.getTeacher().getId())
                .build();
    }
}
