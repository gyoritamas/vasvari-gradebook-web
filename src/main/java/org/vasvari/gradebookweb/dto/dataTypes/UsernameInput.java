package org.vasvari.gradebookweb.dto.dataTypes;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "johndoe72")
    private String username;
}
