package org.vasvari.gradebookweb.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginRequest {
    @NotEmpty(message = "A felhasználónév nem lehet üres")
    private String username;
    @NotEmpty(message = "A jelszó nem lehet üres")
    private String password;
}
