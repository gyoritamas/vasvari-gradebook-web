package org.vasvari.gradebookweb.business.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.vasvari.gradebookweb.business.dto.dataTypes.SimpleStudent;
import org.vasvari.gradebookweb.business.dto.dataTypes.SimpleTeacher;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubjectOutput {
    private Long id;
    private String name;
    private SimpleTeacher teacher;
    private List<SimpleStudent> students;

}
