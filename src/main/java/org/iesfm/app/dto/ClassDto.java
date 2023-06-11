package org.iesfm.app.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Set;

/**
 * En esta clase describen los campos que tendra el dto(Data Transfer Object) de la entidad clase
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassDto {


    @Positive
    private Integer id;

    @NotBlank
    private String name;

    @NotNull
    private UserDto userCre;

    private UserDto userMod;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull
    private LocalDate dateCre;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateMod;


    private Set<UserDto> students;


    private Set<UserDto> teachers;

}