package org.vasvari.gradebookweb.service.gateway;

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
import org.vasvari.gradebookweb.dto.GradebookInput;
import org.vasvari.gradebookweb.model.GradebookEntry;
import org.vasvari.gradebookweb.model.GradebookOutputModel;
import org.vasvari.gradebookweb.util.JwtTokenUtil;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;

@Service
@ComponentScan
@RequiredArgsConstructor
public class GradebookGateway {

    @Value("${api.url}")
    private String baseUrl;

    private final JwtTokenUtil jwtTokenUtil;
    private final RestTemplate template;

    public ResponseEntity<GradebookOutputModel> findGradebookEntryById(Long id) {
        return template.getForEntity(baseUrl + "/gradebook/{id}", GradebookOutputModel.class, id);
    }

    public Collection<GradebookEntry> findAllGradebookEntries() {
        Traverson traverson = new Traverson(URI.create(baseUrl + "/gradebook"), MediaTypes.HAL_JSON);
        TypeReferences.CollectionModelType<GradebookEntry> collectionModelType =
                new TypeReferences.CollectionModelType<>() {
                };

        try {
            CollectionModel<GradebookEntry> gradebookResource = traverson
                    .follow("$._links.self.href")
                    .withHeaders(jwtTokenUtil.getAuthorizationHeaderWithToken())
                    .toObject(collectionModelType);

            if (gradebookResource != null)
                return gradebookResource.getContent();
            else
                return Collections.emptyList();
        } catch (IllegalStateException e) {
            throw new RuntimeException("Unauthorized");
        }
    }

    public Collection<GradebookEntry> findGradebookEntriesOfStudent(Long studentId) {
        Traverson traverson = new Traverson(URI.create(baseUrl + "/student_gradebook/" + studentId), MediaTypes.HAL_JSON);
        TypeReferences.CollectionModelType<GradebookEntry> collectionModelType =
                new TypeReferences.CollectionModelType<>() {
                };

        try {
            CollectionModel<GradebookEntry> gradebookResource = traverson
                    .follow("$._links.student_gradebook.href")
                    .withHeaders(jwtTokenUtil.getAuthorizationHeaderWithToken())
                    .toObject(collectionModelType);

            if (gradebookResource != null)
                return gradebookResource.getContent();
            else
                return Collections.emptyList();
        } catch (IllegalStateException e) {
            throw new RuntimeException("Unauthorized");
        }
    }

    public void save(GradebookInput gradebookEntry) {
        template.postForEntity(baseUrl + "/gradebook", gradebookEntry, EntityModel.class);
    }

    // update and delete methods are not supported

}
