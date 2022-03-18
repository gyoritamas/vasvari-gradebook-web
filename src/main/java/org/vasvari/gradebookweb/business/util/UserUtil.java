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
        return UserRole.valueOf(
                jwtTokenUtil.getUserRoleFromToken(tokenRepository.getToken().getTokenString())
        );
    }

    public String username() {
        return jwtTokenUtil.getUsernameFromToken(tokenRepository.getToken().getTokenString());
    }
}
