package org.iesfm.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank
    private String name;

    @NotBlank
    private String firstSurname;


    private String secondSurname;


    @NotNull
    private LocalDate dateCre;


    private LocalDate dateMod;


    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String pass;

    @NotNull
    private RoleDto role;


}
