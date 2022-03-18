package org.vasvari.gradebookweb.business.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentInput {

    @NotBlank(message = "Name field cannot be empty")
    @Schema(example = "Homework 1")
    private String name;

    @Schema(example = "HOMEWORK")
    private AssignmentType type;

    @Schema(example = "Read Chapters 6 and 9.")
    private String description;

    @Future(message = "Deadline must be a date in the future")
    @Schema(example = "2051-01-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    @NotNull(message = "Subject ID cannot be empty")
    @Schema(example = "1")
    private Long subjectId;

}
