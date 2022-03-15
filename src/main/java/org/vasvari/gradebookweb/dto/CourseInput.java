package org.vasvari.gradebookweb.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseInput {

    @NotBlank(message = "A név nem lehet üres")
    @Size(min=2, max=35, message = "Adjon meg 2-35 karakter közötti nevet")
    private String name;

}