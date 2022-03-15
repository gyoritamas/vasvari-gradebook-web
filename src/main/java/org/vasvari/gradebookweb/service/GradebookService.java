package org.vasvari.gradebookweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vasvari.gradebookweb.dto.GradebookInput;
import org.vasvari.gradebookweb.dto.GradebookOutput;
import org.vasvari.gradebookweb.dto.mapper.GradebookEntryMapper;
import org.vasvari.gradebookweb.model.GradebookEntry;
import org.vasvari.gradebookweb.service.gateway.GradebookGateway;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradebookService {

    private final GradebookGateway gateway;
    private final GradebookEntryMapper mapper;

    public GradebookOutput findGradebookEntryById(Long id) {
        GradebookEntry entryFound = gateway.findGradebookEntryById(id).getBody().getContent();

        return mapper.map(entryFound);
    }

    public List<GradebookOutput> findAllGradebookEntries() {
        List<GradebookEntry> entries = new ArrayList<>(gateway.findAllGradebookEntries());

        return mapper.mapAll(entries);
    }

    public List<GradebookOutput> findGradebookEntriesOfStudent(Long studentId) {
        List<GradebookEntry> entriesOfStudent = gateway.findAllGradebookEntries().stream()
                .filter(entry -> entry.getStudentId().equals(studentId))
                .collect(Collectors.toList());

        return mapper.mapAll(entriesOfStudent);
    }

    public List<GradebookOutput> findGradebookEntriesOfClass(Long classId) {
        List<GradebookEntry> entriesOfClass = gateway.findAllGradebookEntries().stream()
                .filter(entry -> entry.getCourseId().equals(classId))
                .collect(Collectors.toList());
        return mapper.mapAll(entriesOfClass);
    }

    public void save(GradebookInput gradebookEntry) {
        gateway.save(gradebookEntry);
    }

}
