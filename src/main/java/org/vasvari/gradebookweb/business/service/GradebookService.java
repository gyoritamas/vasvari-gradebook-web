package org.vasvari.gradebookweb.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vasvari.gradebookweb.business.dto.GradebookOutput;
import org.vasvari.gradebookweb.business.service.gateway.GradebookGateway;
import org.vasvari.gradebookweb.business.dto.GradebookInput;
import org.vasvari.gradebookweb.business.util.UserUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradebookService {

    private final UserUtil userUtil;
    private final GradebookGateway gateway;

    public GradebookOutput findGradebookEntryById(Long id) {
        return gateway.findGradebookEntryById(id);
    }

    public List<GradebookOutput> findGradebookEntriesForUser() {
        switch (userUtil.userRole()) {
            case ADMIN:
                return findAllGradebookEntries();
            case TEACHER:
                return findGradebookEntriesOfTeacherUser();
            case STUDENT:
                return findGradebookEntriesOfStudentUser();
            default:
                throw new RuntimeException("Unrecognised user role");
        }
    }

    public List<GradebookOutput> findAllGradebookEntries() {
        return new ArrayList<>(gateway.findAllGradebookEntries());
    }

    private List<GradebookOutput> findGradebookEntriesOfTeacherUser() {
        return new ArrayList<>(gateway.findGradebookEntriesOfCurrentUserAsTeacher());
    }

    private List<GradebookOutput> findGradebookEntriesOfStudentUser() {
        return new ArrayList<>(gateway.findGradebookEntriesOfCurrentUserAsStudent());
    }

    public List<GradebookOutput> findGradebookEntriesOfStudent(Long studentId) {
        return gateway.findAllGradebookEntries().stream()
                .filter(entry -> entry.getStudent().getId().equals(studentId))
                .collect(Collectors.toList());
    }

    public List<GradebookOutput> findGradebookEntriesOfCourse(Long courseId) {
        return gateway.findAllGradebookEntries().stream()
                .filter(entry -> entry.getSubject().getId().equals(courseId))
                .collect(Collectors.toList());
    }

    public void saveGradebookEntry(GradebookInput gradebookEntry) {
        gateway.saveGradebookEntry(gradebookEntry);
    }

}
