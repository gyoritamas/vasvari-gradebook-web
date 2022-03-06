package org.vasvari.gradebookweb.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vasvari.gradebookweb.dto.ClassInput;
import org.vasvari.gradebookweb.dto.ClassOutput;
import org.vasvari.gradebookweb.jwt.TokenRepository;
import org.vasvari.gradebookweb.model.ClassOutputModel;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;

@Service
@ComponentScan
public class ClassGateway {

    @Value("${api.url}")
    private String baseUrl;

    private final TokenRepository tokenRepository;
    private final RestTemplate restTemplate;

    public ClassGateway(TokenRepository tokenRepository, RestTemplateBuilder builder) {
        this.tokenRepository = tokenRepository;
        this.restTemplate = builder.build();
    }

    public ResponseEntity<ClassOutputModel> findClassById(Long id) {
        return restTemplate.getForEntity(baseUrl + "/classes/{id}", ClassOutputModel.class, id);
    }

    public Collection<ClassOutput> findAllClasses() {
        Traverson traverson = new Traverson(URI.create(baseUrl + "/classes"), MediaTypes.HAL_JSON);
        TypeReferences.CollectionModelType<ClassOutput> collectionModelType =
                new TypeReferences.CollectionModelType<>() {
                };

        try {
            CollectionModel<ClassOutput> classResource = traverson
                    .follow("$._links.self.href")
                    .withHeaders(getAuthorizationHeader())
                    .toObject(collectionModelType);

            if (classResource != null)
                return classResource.getContent();
            else
                return Collections.emptyList();
        } catch (IllegalStateException e) {
            throw new RuntimeException("Unauthorized");
        }
    }

    public ResponseEntity<?> save(ClassInput clazz) {
        return restTemplate.postForEntity(baseUrl + "/classes", clazz, EntityModel.class);
    }

    public void updateClass(Long id, ClassInput update) {
        restTemplate.put(baseUrl + "/classes/{id}", update, id);
    }

    public void deleteClass(Long id) {
        restTemplate.delete(baseUrl + "/classes/{id}", id);
    }

    private HttpHeaders getAuthorizationHeader() {
        HttpHeaders headers = new HttpHeaders();
        if (tokenRepository.getToken() != null) {
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + tokenRepository.getToken().getTokenString());
        }
        return headers;
    }
}