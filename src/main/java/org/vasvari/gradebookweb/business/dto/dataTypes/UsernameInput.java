package org.vasvari.gradebookweb.business.dto.dataTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsernameInput {
    @NotBlank(message = "Username field cannot be empty")
    private String username;
}
