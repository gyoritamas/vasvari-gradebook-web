package org.vasvari.gradebookweb.business.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SubjectInput {

    private Long id;

    @NotBlank(message = "A név nem lehet üres")
    @Size(min=2, max=35, message = "Adjon meg 2-35 karakter közötti nevet")
    private String name;

    @NotNull
    private Long teacherId;
}