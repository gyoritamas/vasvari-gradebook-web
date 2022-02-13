package org.vasvari.gradebookweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GradebookEntry {

    private Long id;

    private Long studentId;

    private Long classId;

    private Long assignmentId;

    private Integer grade;
}