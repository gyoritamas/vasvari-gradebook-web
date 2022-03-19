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
import org.vasvari.gradebookweb.business.dto.AssignmentInput;
import org.vasvari.gradebookweb.business.dto.AssignmentOutput;
import org.vasvari.gradebookweb.business.model.AssignmentOutputModel;
import org.vasvari.gradebookweb.business.util.JwtTokenUtil;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;

@Service
@ComponentScan
@RequiredArgsConstructor
public class AssignmentGateway {

    @Value("${api.url}")
    private String baseUrl;

    private final JwtTokenUtil jwtTokenUtil;
    private final RestTemplate template;

    public AssignmentOutput findAssignmentById(Long id) {
        ResponseEntity<AssignmentOutputModel> response = template.getForEntity(baseUrl + "/assignments/{id}", AssignmentOutputModel.class, id);
        if (response.getStatusCodeValue() != 200 || response.getBody() == null)
            throw new RuntimeException("Failed to find assignment with ID " + id);

        return response.getBody().getContent();
    }

    public Collection<AssignmentOutput> findAllAssignments() {
        Traverson traverson = new Traverson(URI.create(baseUrl + "/assignments"), MediaTypes.HAL_JSON);
        TypeReferences.CollectionModelType<AssignmentOutput> collectionModelType =
                new TypeReferences.CollectionModelType<>() {
                };
        try {
            CollectionModel<AssignmentOutput> assignmentResource = traverson
                    .follow("$._links.self.href")
                    .withHeaders(jwtTokenUtil.getAuthorizationHeaderWithToken())
                    .toObject(collectionModelType);

            if (assignmentResource != null)
                return assignmentResource.getContent();
            else
                return Collections.emptyList();
        } catch (IllegalStateException e) {
            throw new RuntimeException("Unauthorized");
        }
    }

    public void save(AssignmentInput assignment) {
        ResponseEntity<?> response = template.postForEntity(baseUrl + "/assignments", assignment, EntityModel.class);
        if (response.getStatusCodeValue() != 201)
            throw new RuntimeException("Failed to save assignment");
    }

    public void updateAssignment(Long id, AssignmentInput update) {
        template.put(baseUrl + "/assignments/{id}", update, id);
    }

    public void deleteAssignment(Long id) {
        template.delete(baseUrl + "/assignments/{id}", id);
    }

}
