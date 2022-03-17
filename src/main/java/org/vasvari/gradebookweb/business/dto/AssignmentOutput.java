package org.vasvari.gradebookweb.business.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.vasvari.gradebookweb.business.dto.dataTypes.SimpleData;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentOutput {

    private Long id;

    @Schema(example = "Homework 1")
    private String name;

    @Schema(example = "HOMEWORK")
    private AssignmentType type;

    @Schema(example = "Read Chapters 6 and 9.")
    private String description;

    @Schema(example = "2051-01-01")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    private SimpleData course;

}
