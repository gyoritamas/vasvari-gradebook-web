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

    public GradebookOutput findGradebookEntryById(Long id) {
        ResponseEntity<GradebookOutputModel> response = template.getForEntity(baseUrl + "/gradebook/{id}", GradebookOutputModel.class, id);
        if (response.getStatusCodeValue() != 200 || response.getBody() == null)
            // TODO: use custom exception
            throw new RuntimeException("Something went wrong");

        return response.getBody().getContent();
    }

    public Collection<GradebookOutput> findAllGradebookEntries() {
        Traverson traverson = new Traverson(URI.create(baseUrl + "/gradebook"), MediaTypes.HAL_JSON);
        TypeReferences.CollectionModelType<GradebookOutput> collectionModelType =
                new TypeReferences.CollectionModelType<>() {
                };

        try {
            CollectionModel<GradebookOutput> gradebookResource = traverson
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

    public Collection<GradebookOutput> findGradebookEntriesOfStudent(Long studentId) {
        Traverson traverson = new Traverson(URI.create(baseUrl + "/student_gradebook/" + studentId), MediaTypes.HAL_JSON);
        TypeReferences.CollectionModelType<GradebookOutput> collectionModelType =
                new TypeReferences.CollectionModelType<>() {
                };

        try {
            CollectionModel<GradebookOutput> gradebookResource = traverson
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
