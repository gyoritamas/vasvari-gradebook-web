package org.vasvari.gradebookweb.business.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GradebookInput {
    @NotNull(message = "Student ID cannot be empty")
    private Long studentId;

    @NotNull(message = "Course ID cannot be empty")
    private Long courseId;

    @NotNull(message = "Assignment ID cannot be empty")
    private Long assignmentId;

    @NotNull(message = "Grade field cannot be empty")
    @Min(value = 1, message = "Grade value must be between 1-5")
    @Max(value = 5, message = "Grade value must be between 1-5")
    private Integer grade;

}