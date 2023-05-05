package org.iesfm.app.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassDto {


    @Positive
    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
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