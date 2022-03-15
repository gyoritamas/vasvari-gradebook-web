package org.vasvari.gradebookweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentInput {

    @NotBlank(message = "Name field cannot be empty")
    private String name;

    // TODO: use enum?
    private String type;

    private String description;
}
