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
import org.vasvari.gradebookweb.business.dto.CourseInput;
import org.vasvari.gradebookweb.business.dto.CourseOutput;
import org.vasvari.gradebookweb.business.model.CourseOutputModel;
import org.vasvari.gradebookweb.business.util.JwtTokenUtil;

import java.net.URI;
import java.util.*;

@Service
@ComponentScan
@RequiredArgsConstructor
public class CourseGateway {

    @Value("${api.url}")
    private String baseUrl;

    private final JwtTokenUtil jwtTokenUtil;
    private final RestTemplate template;

    public CourseOutput findClassById(Long id) {
        ResponseEntity<CourseOutputModel> response = template.getForEntity(baseUrl + "/classes/{id}", CourseOutputModel.class, id);
        if (response.getStatusCodeValue() != 200 || response.getBody() == null)
            // TODO: use custom exception
            throw new RuntimeException("Something went wrong");

        return response.getBody().getContent();
    }

    public Collection<CourseOutput> findCourses() {
        Traverson traverson = new Traverson(URI.create(baseUrl + "/classes"), MediaTypes.HAL_JSON);
        TypeReferences.CollectionModelType<CourseOutput> collectionModelType =
                new TypeReferences.CollectionModelType<>() {
                };

        try {
            CollectionModel<CourseOutput> courseResource = traverson
                    .follow("$._links.self.href")
                    .withHeaders(jwtTokenUtil.getAuthorizationHeaderWithToken())
                    .toObject(collectionModelType);

            if (courseResource != null)
                return courseResource.getContent();
            else
                return Collections.emptyList();
        } catch (IllegalStateException e) {
            // TODO: use custom exception
            throw new RuntimeException("Unauthorized");
        }
    }

    public void save(CourseInput course) {
        ResponseEntity<?> response = template.postForEntity(baseUrl + "/classes", course, EntityModel.class);
        // TODO: use custom exception
        if (response.getStatusCodeValue() != 201 || response.getBody() == null)
            throw new RuntimeException("Something went wrong");
    }

    public void updateClass(Long id, CourseInput update) {
        template.put(baseUrl + "/classes/{id}", update, id);
    }

    public void deleteClass(Long id) {
        template.delete(baseUrl + "/classes/{id}", id);
    }

}