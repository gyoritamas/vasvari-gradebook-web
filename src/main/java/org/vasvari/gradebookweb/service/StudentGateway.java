package org.vasvari.gradebookweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vasvari.gradebookweb.dto.StudentDto;
import org.vasvari.gradebookweb.jwt.TokenRepository;
import org.vasvari.gradebookweb.model.StudentOutputModel;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;

@Service
public class StudentGateway {

    @Value("${api.url}")
    private String baseUrl;

    private final TokenRepository tokenRepository;
    private final RestTemplate template;

    @Autowired
    public StudentGateway(TokenRepository tokenRepository, RestTemplate restTemplate) {
        this.tokenRepository = tokenRepository;
        this.template = restTemplate;
    }

    public ResponseEntity<StudentOutputModel> findStudentById(Long id) {

        return template.getForEntity(baseUrl + "/students/{id}", StudentOutputModel.class, id);
    }

    public Collection<StudentDto> findAllStudents() {
        Traverson traverson = new Traverson(URI.create(baseUrl + "/students"), MediaTypes.HAL_JSON);
        TypeReferences.CollectionModelType<StudentDto> collectionModelType =
                new TypeReferences.CollectionModelType<>() {
                };

        try {
            CollectionModel<StudentDto> studentResource = traverson
                    .follow("$._links.self.href")
                    .withHeaders(getAuthorizationHeader())
                    .toObject(collectionModelType);

            if (studentResource != null)
                return studentResource.getContent();
            else
                return Collections.emptyList();
        } catch (IllegalStateException e) {
            throw new RuntimeException("Unauthorized");
        }
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

    private HttpHeaders getAuthorizationHeader() {
        HttpHeaders headers = new HttpHeaders();
        if (tokenRepository.getToken() != null) {
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRepository.getToken().getTokenString());
        }
        return headers;
    }
}
