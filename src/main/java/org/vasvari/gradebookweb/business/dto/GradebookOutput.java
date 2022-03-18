package org.vasvari.gradebookweb.business.dto;

import lombok.*;
import org.vasvari.gradebookweb.business.dto.dataTypes.SimpleData;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradebookOutput {
    private Long id;
    private SimpleData student;
    private SimpleData subject;
    private SimpleData assignment;
    private Integer grade;

}
