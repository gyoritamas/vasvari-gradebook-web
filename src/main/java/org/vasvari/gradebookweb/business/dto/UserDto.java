package org.vasvari.gradebookweb.business.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private UserRole role;
}
