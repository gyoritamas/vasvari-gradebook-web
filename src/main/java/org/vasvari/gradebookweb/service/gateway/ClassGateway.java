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
import org.vasvari.gradebookweb.dto.ClassInput;
import org.vasvari.gradebookweb.dto.CourseOutput;
import org.vasvari.gradebookweb.model.ClassOutputModel;
import org.vasvari.gradebookweb.util.JwtTokenUtil;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;

@Service
@ComponentScan
@RequiredArgsConstructor
public class ClassGateway {

    @Value("${api.url}")
    private String baseUrl;

    private final JwtTokenUtil jwtTokenUtil;
    private final RestTemplate template;

    public CourseOutput findClassById(Long id) {
        ResponseEntity<ClassOutputModel> response = template.getForEntity(baseUrl + "/classes/{id}", ClassOutputModel.class, id);
        if (response.getStatusCodeValue() != 200 || response.getBody() == null)
            // TODO: use custom exception
            throw new RuntimeException("Something went wrong");

        return response.getBody().getContent();
    }

    public Collection<CourseOutput> findAllClasses() {
        Traverson traverson = new Traverson(URI.create(baseUrl + "/classes"), MediaTypes.HAL_JSON);
        TypeReferences.CollectionModelType<CourseOutput> collectionModelType =
                new TypeReferences.CollectionModelType<>() {
                };

        try {
            CollectionModel<CourseOutput> classResource = traverson
                    .follow("$._links.self.href")
                    .withHeaders(jwtTokenUtil.getAuthorizationHeaderWithToken())
                    .toObject(collectionModelType);

            if (classResource != null)
                return classResource.getContent();
            else
                return Collections.emptyList();
        } catch (IllegalStateException e) {
            // TODO: use custom exception
            throw new RuntimeException("Unauthorized");
        }
    }

    public void save(ClassInput clazz) {
        ResponseEntity<?> response = template.postForEntity(baseUrl + "/classes", clazz, EntityModel.class);
        // TODO: use custom exception
        if (response.getStatusCodeValue() != 201) throw new RuntimeException("Something went wrong");
    }

    public void updateClass(Long id, ClassInput update) {
        template.put(baseUrl + "/classes/{id}", update, id);
    }

    public void deleteClass(Long id) {
        template.delete(baseUrl + "/classes/{id}", id);
    }

}