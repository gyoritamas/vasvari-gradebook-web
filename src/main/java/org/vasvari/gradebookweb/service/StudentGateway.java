package org.vasvari.gradebookweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.vasvari.gradebookweb.util.JwtTokenUtil;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;

@Service
public class StudentGateway {

    @Value("${api.url}")
    private String baseUrl;

    private final JwtTokenUtil jwtTokenUtil;
    private final RestTemplate template;

    @Autowired
    public StudentGateway(JwtTokenUtil jwtTokenUtil, RestTemplate restTemplate) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.template = restTemplate;
    }

    public StudentDto findStudentById(Long id) {
        ResponseEntity<StudentOutputModel> response = template.getForEntity(baseUrl + "/students/{id}", StudentOutputModel.class, id);
        if (response.getStatusCodeValue() != 200 || response.getBody() == null)
            throw new RuntimeException("Something went wrong");

        return response.getBody().getContent();
    }

    public Collection<StudentDto> findAllStudents() {
        Traverson traverson = new Traverson(URI.create(baseUrl + "/students"), MediaTypes.HAL_JSON);
        TypeReferences.CollectionModelType<StudentDto> collectionModelType =
                new TypeReferences.CollectionModelType<>() {
                };

        try {
            CollectionModel<StudentDto> studentResource = traverson
                    .follow("$._links.self.href")
                    .withHeaders(jwtTokenUtil.getAuthorizationHeaderWithToken())
                    .toObject(collectionModelType);

            if (studentResource != null)
                return studentResource.getContent();
            else
                return Collections.emptyList();
        } catch (IllegalStateException e) {
            throw new RuntimeException("Unauthorized");
        }
    }

    public void save(StudentDto student) {
        ResponseEntity<?> response = template.postForEntity(baseUrl + "/students", student, EntityModel.class);

        if (response.getStatusCodeValue() != 201) throw new RuntimeException("Something went wrong");
    }

    public void updateStudent(Long id, StudentDto update) {
        template.put(baseUrl + "/students/{id}", update, id);
    }

    public void deleteStudent(Long id) {
        template.delete(baseUrl + "/students/{id}", id);
    }

}
