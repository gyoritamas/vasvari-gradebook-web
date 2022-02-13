package org.vasvari.gradebookweb.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vasvari.gradebookweb.dto.StudentDto;
import org.vasvari.gradebookweb.model.StudentOutputModel;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;

@Service
public class StudentGateway {
    @Value("${api.url}")
    private String baseUrl;

    private final RestTemplate template;

    public StudentGateway(RestTemplateBuilder builder) {
        this.template = builder.build();
    }

    public ResponseEntity<StudentOutputModel> findStudentById(Long id) {
        return template.getForEntity(baseUrl + "/students/{id}", StudentOutputModel.class, id);
    }

    public Collection<StudentDto> findAllStudents() {
        Traverson traverson = new Traverson(URI.create(baseUrl + "/students"), MediaTypes.HAL_JSON);
        TypeReferences.CollectionModelType<StudentDto> collectionModelType =
                new TypeReferences.CollectionModelType<>() {
                };

        CollectionModel<StudentDto> studentResource = traverson
                .follow("$._links.self.href")
                .toObject(collectionModelType);

        if (studentResource != null)
            return studentResource.getContent();
        else
            return Collections.emptyList();
    }

    public ResponseEntity<?> save(StudentDto student) {
        return template.postForEntity(baseUrl + "/students", student, EntityModel.class);
    }

    public void updateStudent(Long id, StudentDto update) {
        template.put(baseUrl + "/students/{id}", update, id);
    }

    public void deleteStudent(Long id) {
        template.delete(baseUrl + "/students/{id}", id);
    }
}
