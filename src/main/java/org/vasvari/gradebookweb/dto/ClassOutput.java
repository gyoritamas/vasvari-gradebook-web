package org.vasvari.gradebookweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClassOutput{

    private Long id;

    private String name;

    private List<String> students;

}
