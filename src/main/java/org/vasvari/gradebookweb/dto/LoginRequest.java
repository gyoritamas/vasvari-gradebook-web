package org.vasvari.gradebookweb.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginRequest {
    private String username;
    private String password;
}
