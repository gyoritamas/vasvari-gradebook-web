package org.vasvari.gradebookweb.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vasvari.gradebookweb.dto.GradebookOutput;
import org.vasvari.gradebookweb.dto.dataTypes.SimpleData;
import org.vasvari.gradebookweb.model.GradebookEntry;
import org.vasvari.gradebookweb.service.AssignmentService;
import org.vasvari.gradebookweb.service.ClassService;
import org.vasvari.gradebookweb.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GradebookEntryMapper {
    private final StudentService studentService;
    private final ClassService classService;
    private final AssignmentService assignmentService;

    public GradebookOutput map(GradebookEntry entry) {
        return GradebookOutput.builder()
                .id(entry.getId())
                .student(
                        SimpleData.builder()
                                .id(entry.getStudentId())
                                .name(studentService.findStudentById(entry.getStudentId()).getName())
                                .build()
                )
                .course(
                        SimpleData.builder()
                                .id(entry.getCourseId())
                                .name(classService.findClassById(entry.getCourseId()).getName())
                                .build()
                )
                .assignment(
                        SimpleData.builder()
                                .id(entry.getAssignmentId())
                                .name(assignmentService.findAssignmentById(entry.getAssignmentId()).getName())
                                .build()
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
