package org.vasvari.gradebookweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentDto {
    private Long id;

    @NotBlank(message = "A keresztnév nem lehet üres")
    @Size(min = 2, message = "A keresztnév legalább 2 karakter hosszú kell legyen")
    private String firstname;

    @NotBlank(message = "A vezetéknév nem lehet üres")
    @Size(min = 2, message = "A vezetéknév legalább 2 karakter hosszú kell legyen")
    private String lastname;

    @NotNull(message = "Az évfolyam nem lehet üres")
    @Min(value = 1, message = "Az évfolyam 1-12 érték kell legyen")
    @Max(value = 12, message = "Az évfolyam 1-12 érték kell legyen")
    private Integer gradeLevel;

    @NotBlank(message = "Az email mező nem lehet üres")
    @Email(message = "Az email formátuma hibás")
    private String email;

    @NotBlank(message = "A cím mező nem lehet üres")
    private String address;

    @NotBlank(message = "A telefonszám nem lehet üres")
    @Pattern(regexp = "^\\+?[\\d \\-()]{7,}",
            message = "A telefonszám formátuma hibás")
    private String phone;

    @NotBlank(message = "A születési dátum nem lehet üres")
    private String birthdate;

    public String getName() {
        return lastname + " " + firstname;
    }
}
