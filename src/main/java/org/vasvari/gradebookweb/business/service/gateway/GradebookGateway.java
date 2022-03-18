package org.vasvari.gradebookweb.business.service.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vasvari.gradebookweb.business.dto.GradebookOutput;
import org.vasvari.gradebookweb.business.dto.GradebookInput;
import org.vasvari.gradebookweb.business.model.GradebookOutputModel;
import org.vasvari.gradebookweb.business.util.JwtTokenUtil;
import org.vasvari.gradebookweb.business.util.TraversonUtil;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;

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
            throw new RuntimeException("Something went wrong");

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
        String url = baseUrl + "/student_gradebook";
        String linkTo = "student_gradebook";

        return traversonUtil.getGradebookOutputCollection(url, linkTo);
    }

    public void saveGradebookEntry(GradebookInput gradebookEntry) {
        template.postForEntity(baseUrl + "/gradebook", gradebookEntry, EntityModel.class);
    }

    // update and delete methods are not supported

}
