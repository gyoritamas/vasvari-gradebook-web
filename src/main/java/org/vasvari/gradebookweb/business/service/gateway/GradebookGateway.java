package org.vasvari.gradebookweb.business.service.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vasvari.gradebookweb.business.dto.GradebookOutput;
import org.vasvari.gradebookweb.business.dto.GradebookInput;
import org.vasvari.gradebookweb.business.model.GradebookOutputModel;
import org.vasvari.gradebookweb.business.util.TraversonUtil;

import java.util.Collection;

@Service
@ComponentScan
@RequiredArgsConstructor
public class GradebookGateway {

    @Value("${api.url}")
    private String baseUrl;

    private final TraversonUtil traversonUtil;
    private final RestTemplate template;

    public GradebookOutput findGradebookEntryById(Long id) {
        ResponseEntity<GradebookOutputModel> response = template.getForEntity(baseUrl + "/gradebook/{id}", GradebookOutputModel.class, id);
        if (response.getStatusCodeValue() != 200 || response.getBody() == null)
            // TODO: use custom exception
            throw new RuntimeException("Failed to find gradebook entry with ID " + id);

        return response.getBody().getContent();
    }

    public Collection<GradebookOutput> findAllGradebookEntries() {
        String url = baseUrl + "/gradebook";
        String linkTo = "self";

        return traversonUtil.getGradebookOutputCollection(url, linkTo);
    }

    public Collection<GradebookOutput> findGradebookEntriesOfCurrentUserAsTeacher() {
        String url = baseUrl + "/teacher-user/gradebook-entries";
        String linkTo = "gradebook-entries-of-teacher";

        return traversonUtil.getGradebookOutputCollection(url, linkTo);
    }

    public Collection<GradebookOutput> findGradebookEntriesOfCurrentUserAsStudent() {
        String url = baseUrl + "/student-user/gradebook-entries";
        String linkTo = "gradebook-entries-of-student";

        return traversonUtil.getGradebookOutputCollection(url, linkTo);
    }

    public Collection<GradebookOutput> findGradebookEntriesOfStudent(Long studentId) {
        String url = String.format("%s/student_gradebook/%s", baseUrl, studentId);
        String linkTo = "student_gradebook";

        return traversonUtil.getGradebookOutputCollection(url, linkTo);
    }

    public Collection<GradebookOutput> findGradebookEntriesOfSubject(Long subjectId) {
        String url = String.format("%s/subject_gradebook/%s", baseUrl, subjectId);
        String linkTo = "subject_gradebook";

        return traversonUtil.getGradebookOutputCollection(url, linkTo);
    }

    public void saveGradebookEntry(GradebookInput gradebookEntry) {
        template.postForEntity(baseUrl + "/gradebook", gradebookEntry, EntityModel.class);
    }

    public void updateGradebookEntry(Long id, GradebookInput update) {
        throw new RuntimeException("Method not implemented yet");
    }

    public void deleteGradebookEntry(Long id) {
        template.delete(String.format("%s/gradebook/%s", baseUrl, id));
    }

}
