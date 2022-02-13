package org.vasvari.gradebookweb.dto;

import lombok.*;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClassOutput {

    private Long id;

    private String course;

    private List<String> students;

}
