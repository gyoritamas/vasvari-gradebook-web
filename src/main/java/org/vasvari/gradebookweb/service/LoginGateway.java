package org.vasvari.gradebookweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vasvari.gradebookweb.dto.LoginRequest;
import org.vasvari.gradebookweb.dto.LoginResponse;
import org.vasvari.gradebookweb.jwt.TokenRepository;

@Service
public class LoginGateway {
    @Value("${api.url}")
    private String baseUrl;

    @Autowired
    private TokenRepository tokenRepository;

    private final RestTemplate template;

    public LoginGateway(RestTemplateBuilder builder) {
        this.template = builder.build();
    }

    public void login(LoginRequest loginRequest) {
        LoginResponse response = template.postForObject(baseUrl + "/authenticate", loginRequest, LoginResponse.class);
        String token = response.getJwtToken();
        tokenRepository.setToken(token);
    }

    public void logout() {
        tokenRepository.setToken(null);
    }
}
