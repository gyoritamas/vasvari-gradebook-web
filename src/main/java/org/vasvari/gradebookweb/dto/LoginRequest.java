package org.vasvari.gradebookweb.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
