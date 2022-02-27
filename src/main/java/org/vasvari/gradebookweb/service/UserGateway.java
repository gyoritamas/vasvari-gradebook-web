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
import org.vasvari.gradebookweb.dto.UserDto;
import org.vasvari.gradebookweb.model.StudentOutputModel;
import org.vasvari.gradebookweb.model.UserOutputModel;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;

@Service
public class UserGateway {
    @Value("${api.url}")
    private String baseUrl;

    private final RestTemplate template;

    public UserGateway(RestTemplateBuilder builder) {
        this.template = builder.build();
    }

    public ResponseEntity<UserOutputModel> findUserById(Long id) {
        return template.getForEntity(baseUrl + "/users/{id}", UserOutputModel.class, id);
    }

    public Collection<UserDto> findAllUsers() {
        Traverson traverson = new Traverson(URI.create(baseUrl + "/users"), MediaTypes.HAL_JSON);
        TypeReferences.CollectionModelType<UserDto> collectionModelType =
                new TypeReferences.CollectionModelType<>() {
                };

        CollectionModel<UserDto> userResource = traverson
                .follow("$._links.self.href")
                .toObject(collectionModelType);

        if (userResource != null)
            return userResource.getContent();
        else
            return Collections.emptyList();
    }

    public ResponseEntity<?> save(UserDto user) {
        return template.postForEntity(baseUrl + "/users", user, EntityModel.class);
    }

    public void updateUser(Long id, UserDto update) {
        template.put(baseUrl + "/users/{id}", update, id);
    }

    public void deleteUser(Long id) {
        template.delete(baseUrl + "/users/{id}", id);
    }


}