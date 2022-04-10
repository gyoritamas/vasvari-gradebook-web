package org.vasvari.gradebookweb.business.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.vasvari.gradebookweb.business.dto.UserRole;
import org.vasvari.gradebookweb.business.jwt.TokenRepository;

@Component
@RequiredArgsConstructor
public class UserUtil {

    private final TokenRepository tokenRepository;
    private final JwtTokenUtil jwtTokenUtil;

    public UserRole userRole() {
        if (isUnauthenticated()) throw new RuntimeException("Unauthenticated user");

        return UserRole.valueOf(
                jwtTokenUtil.getUserRoleFromToken(tokenRepository.getToken().getTokenString())
        );
    }

    public String username() {
        return jwtTokenUtil.getUsernameFromToken(tokenRepository.getToken().getTokenString());
    }

    public boolean isUnauthenticated() {
        return tokenRepository.getToken() == null;
    }

    public boolean hasAnyRole(String... roles) {
        boolean hasAnyRole = false;
        for (String role : roles) {
            hasAnyRole = hasAnyRole || (userRole() == UserRole.valueOf(role));
        }

        return hasAnyRole;
    }

}
