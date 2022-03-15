package org.vasvari.gradebookweb.dto;

import lombok.*;
import org.vasvari.gradebookweb.dto.dataTypes.SimpleData;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradebookOutput {
    private Long id;
    private SimpleData student;
    private SimpleData course;
    private SimpleData assignment;
    private Integer grade;

}
