package org.vasvari.gradebookweb.business.service.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vasvari.gradebookweb.business.dto.TeacherDto;
import org.vasvari.gradebookweb.business.model.TeacherOutputModel;
import org.vasvari.gradebookweb.business.util.JwtTokenUtil;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class TeacherGateway {

    @Value("${api.url}")
    private String baseUrl;

    private final JwtTokenUtil jwtTokenUtil;
    private final RestTemplate template;

    public TeacherDto findTeacherById(Long id) {
        ResponseEntity<TeacherOutputModel> response = template.getForEntity(baseUrl + "/teachers/{id}", TeacherOutputModel.class, id);
        if (response.getStatusCodeValue() != 200 || response.getBody() == null)
            throw new RuntimeException("Something went wrong");

        return response.getBody().getContent();
    }

    public Collection<TeacherDto> findAllTeachers() {
        Traverson traverson = new Traverson(URI.create(baseUrl + "/teachers"), MediaTypes.HAL_JSON);
        TypeReferences.CollectionModelType<TeacherDto> collectionModelType =
                new TypeReferences.CollectionModelType<>() {
                };

        try {
            CollectionModel<TeacherDto> teacherResource = traverson
                    .follow("$._links.self.href")
                    .withHeaders(jwtTokenUtil.getAuthorizationHeaderWithToken())
                    .toObject(collectionModelType);

            if (teacherResource != null)
                return teacherResource.getContent();
            else
                return Collections.emptyList();
        } catch (IllegalStateException e) {
            throw new RuntimeException("Unauthorized");
        }
    }

    public void save(TeacherDto teacher) {
        ResponseEntity<?> response = template.postForEntity(baseUrl + "/teachers", teacher, EntityModel.class);

        if (response.getStatusCodeValue() != 201) throw new RuntimeException("Something went wrong");
    }

    public void updateTeacher(Long id, TeacherDto update) {
        template.put(baseUrl + "/teachers/{id}", update, id);
    }

    public void deleteTeacher(Long id) {
        template.delete(baseUrl + "/teachers/{id}", id);
    }

}
