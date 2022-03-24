package org.vasvari.gradebookweb.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vasvari.gradebookweb.business.dto.UserDto;
import org.vasvari.gradebookweb.business.dto.dataTypes.InitialCredentials;
import org.vasvari.gradebookweb.business.dto.dataTypes.UsernameInput;
import org.vasvari.gradebookweb.business.service.gateway.UserGateway;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<UserDto> findStudentUser(Long studentId) {
        return gateway.findStudentUser(studentId);
    }

    public Optional<UserDto> findTeacherUser(Long teacherId) {
        return gateway.findTeacherUser(teacherId);
    }

    public void saveUser(UserDto user) {
        gateway.saveUser(user);
    }

    public void updateUser(Long id, UserDto update) {
        gateway.updateUser(id, update);
    }

    public void enableUser(Long userId) {
        gateway.enableUser(userId);
    }

    public void disableUser(Long userId) {
        gateway.disableUser(userId);
    }

    public InitialCredentials createStudentUser(Long studentId) {
        return gateway.createStudentUser(studentId);
    }

    public InitialCredentials createTeacherUser(Long teacherId) {
        return gateway.createTeacherUser(teacherId);
    }

    public InitialCredentials createAdminUser(UsernameInput username) {
        return gateway.createAdminUser(username);
    }

    public void deleteUser(Long id) {
        gateway.deleteUser(id);
    }
}
