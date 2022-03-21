package org.vasvari.gradebookweb.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vasvari.gradebookweb.business.dto.UserDto;
import org.vasvari.gradebookweb.business.service.gateway.UserGateway;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserGateway gateway;

    public UserDto findUserById(Long id) {
        return gateway.findUserById(id);
    }

    public List<UserDto> findAllUsers() {
        return new ArrayList<>(gateway.findAllUsers());
    }

    public void saveUser(UserDto user) {
        gateway.saveUser(user);
    }

    public void updateUser(Long id, UserDto update) {
        gateway.updateUser(id, update);
    }

    public void deleteUser(Long id) {
        gateway.deleteUser(id);
    }
}
