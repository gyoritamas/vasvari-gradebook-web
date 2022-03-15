package org.vasvari.gradebookweb.business.jwt;

import org.springframework.stereotype.Repository;

@Repository
public class TokenRepository {
    private JwtToken jwtToken;

    public void setToken(String token) {
        this.jwtToken = new JwtToken(token);
    }

    public JwtToken getToken() {
        return this.jwtToken;
    }
}
