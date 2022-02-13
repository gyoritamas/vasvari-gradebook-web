package org.vasvari.gradebookweb.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vasvari.gradebookweb.dto.AssignmentInput;
import org.vasvari.gradebookweb.dto.AssignmentOutput;
import org.vasvari.gradebookweb.model.AssignmentOutputModel;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;

@Service
@ComponentScan
public class AssignmentGateway {

    @Value("${api.url}")
    private String baseUrl;

    private final RestTemplate template;

    public AssignmentGateway(RestTemplateBuilder builder) {
        this.template = builder.build();
    }

    public ResponseEntity<AssignmentOutputModel> findAssignmentById(Long id) {
        return template.getForEntity(baseUrl + "/assignments/{id}", AssignmentOutputModel.class, id);
    }

    public Collection<AssignmentOutput> findAllAssignments() {
        Traverson traverson = new Traverson(URI.create(baseUrl + "/assignments"), MediaTypes.HAL_JSON);
        TypeReferences.CollectionModelType<AssignmentOutput> collectionModelType =
                new TypeReferences.CollectionModelType<>() {
                };

        CollectionModel<AssignmentOutput> assignmentResource = traverson
                .follow("$._links.self.href")
                .toObject(collectionModelType);

        if (assignmentResource != null)
            return assignmentResource.getContent();
        else
            return Collections.emptyList();
    }

    public void save(AssignmentInput assignment) {
        template.postForEntity(baseUrl + "/assignments", assignment, EntityModel.class);
    }

    public void updateAssignment(Long id, AssignmentInput update) {
        template.put(baseUrl + "/assignments/{id}", update, id);
    }

    public void deleteAssignment(Long id) {
        template.delete(baseUrl + "/assignments/{id}", id);
    }
}
