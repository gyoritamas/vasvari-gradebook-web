package org.vasvari.gradebookweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.vasvari.gradebookweb.jwt.JwtTokenInjectionInterceptor;

import java.util.Collections;

@Configuration
public class ClientConfig {

    // Injection interceptor. The interceptor can also create new instances here without being declared Bean.
    @Autowired
    JwtTokenInjectionInterceptor interceptor;

    // Declare as Bean to facilitate the use of the same instance within the application
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Adding a custom Client HttpRequest Interceptor to RestTemplate adds more than one
        restTemplate.setInterceptors(Collections.singletonList(interceptor));
        return restTemplate;
    }
}
