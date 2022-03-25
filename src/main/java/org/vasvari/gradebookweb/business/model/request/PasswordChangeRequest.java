package org.vasvari.gradebookweb.business.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PasswordChangeRequest {
    private String oldPassword;
    private String newPassword;
}
