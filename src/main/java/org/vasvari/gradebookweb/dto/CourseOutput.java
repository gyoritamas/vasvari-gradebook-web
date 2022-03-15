package org.vasvari.gradebookweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.vasvari.gradebookweb.dto.dataTypes.SimpleData;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseOutput {
    private Long id;
    private String name;
    private SimpleData teacher;
    private List<SimpleData> students;

}
