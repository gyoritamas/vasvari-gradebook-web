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

    public UserDto findUserById(Long id) {
        ResponseEntity<UserOutputModel> response = template.getForEntity(baseUrl + "/users/{id}", UserOutputModel.class, id);
        if (response.getStatusCodeValue() != 200 || response.getBody() == null)
            throw new RuntimeException("Failed to find user with ID " + id);

        return response.getBody().getContent();
    }

    public Collection<UserDto> findAllUsers() {
        String url = baseUrl + "/users";
        String linkTo = "self";

        return traversonUtil.getUserDtoCollection(url, linkTo);
    }

    public void saveUser(UserDto user) {
        ResponseEntity<?> response = template.postForEntity(baseUrl + "/users", user, EntityModel.class);

        if (response.getStatusCodeValue() != 201) throw new RuntimeException("Failed to save user");
    }

    public void updateUser(Long id, UserDto update) {
        template.put(baseUrl + "/users/{id}", update, id);
    }

    public void deleteUser(Long id) {
        template.delete(baseUrl + "/users/{id}", id);
    }

}
