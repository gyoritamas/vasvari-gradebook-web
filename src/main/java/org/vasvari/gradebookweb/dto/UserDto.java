package org.vasvari.gradebookweb.dto;

import lombok.*;
import org.springframework.hateoas.server.core.Relation;

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
