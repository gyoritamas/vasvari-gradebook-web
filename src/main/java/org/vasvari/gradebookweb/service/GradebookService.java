package org.vasvari.gradebookweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vasvari.gradebookweb.dto.GradebookInput;
import org.vasvari.gradebookweb.dto.GradebookOutput;
import org.vasvari.gradebookweb.service.gateway.GradebookGateway;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradebookService {

    private final GradebookGateway gateway;

    public GradebookOutput findGradebookEntryById(Long id) {
        return gateway.findGradebookEntryById(id);
    }

    public List<GradebookOutput> findAllGradebookEntries() {
        return new ArrayList<>(gateway.findAllGradebookEntries());
    }

    public List<GradebookOutput> findGradebookEntriesOfStudent(Long studentId) {
        return gateway.findAllGradebookEntries().stream()
                .filter(entry -> entry.getStudent().getId().equals(studentId))
                .collect(Collectors.toList());
    }

    public List<GradebookOutput> findGradebookEntriesOfCourse(Long courseId) {
        return gateway.findAllGradebookEntries().stream()
                .filter(entry -> entry.getCourse().getId().equals(courseId))
                .collect(Collectors.toList());
    }

    public void save(GradebookInput gradebookEntry) {
        gateway.save(gradebookEntry);
    }

}
