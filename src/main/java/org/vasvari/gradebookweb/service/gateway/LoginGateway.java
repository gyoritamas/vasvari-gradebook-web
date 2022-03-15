package org.vasvari.gradebookweb.service.gateway;

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

    private final TokenRepository tokenRepository;
    private final RestTemplate template;

    // not using the restTemplate bean from ClientConfig here
    // since we don't want to send any token
    public LoginGateway(TokenRepository tokenRepository, RestTemplateBuilder builder) {
        this.tokenRepository = tokenRepository;
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
