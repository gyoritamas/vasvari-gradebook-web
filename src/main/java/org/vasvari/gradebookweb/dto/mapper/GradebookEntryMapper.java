package org.vasvari.gradebookweb.dto.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vasvari.gradebookweb.dto.GradebookOutput;
import org.vasvari.gradebookweb.model.GradebookEntry;
import org.vasvari.gradebookweb.service.AssignmentService;
import org.vasvari.gradebookweb.service.ClassService;
import org.vasvari.gradebookweb.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GradebookEntryMapper {
    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassService classService;

    @Autowired
    private AssignmentService assignmentService;

    public GradebookOutput map(GradebookEntry entry) {
        return GradebookOutput.builder()
                .id(entry.getId())
                .studentId(entry.getStudentId())
                .studentName(
                        studentService.findStudentById(entry.getStudentId()).getName()
                )
                .classId(entry.getCourseId())
                .className(
                        classService.findClassById(entry.getCourseId()).getName()
                )
                .assignmentId(entry.getAssignmentId())
                .assignmentName(
                        assignmentService.findAssignmentById(entry.getAssignmentId()).getName()
                )
                .grade(entry.getGrade())
                .build();
    }

    public List<GradebookOutput> mapAll(List<GradebookEntry> entries) {
        return entries.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
