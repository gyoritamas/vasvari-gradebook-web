package org.vasvari.gradebookweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.vasvari.gradebookweb.dto.LoginRequest;
import org.vasvari.gradebookweb.service.LoginGateway;

@RestController
public class LoginController {
    @Autowired
    private LoginGateway loginGateway;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        loginGateway.login(loginRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        loginGateway.logout();
        return ResponseEntity.ok().build();
    }
}
