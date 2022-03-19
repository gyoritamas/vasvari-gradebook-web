package org.vasvari.gradebookweb.business.service.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vasvari.gradebookweb.business.dto.UserDto;
import org.vasvari.gradebookweb.business.model.UserOutputModel;
import org.vasvari.gradebookweb.business.util.TraversonUtil;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserGateway {
    @Value("${api.url}")
    private String baseUrl;

    private final TraversonUtil traversonUtil;
    private final RestTemplate template;

    public ResponseEntity<UserOutputModel> findUserById(Long id) {
        return template.getForEntity(baseUrl + "/users/{id}", UserOutputModel.class, id);
    }

    public Collection<UserDto> findAllUsers() {
        String url = baseUrl + "/users";
        String linkTo = "self";

        return traversonUtil.getUserDtoCollection(url, linkTo);
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
