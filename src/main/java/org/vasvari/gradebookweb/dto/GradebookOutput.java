package org.vasvari.gradebookweb.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradebookOutput {

    private Long id;

    private Long studentId;
    private String studentName;

    private Long classId;
    private String className;

    private Long assignmentId;
    private String assignmentName;

    private Integer grade;
}
