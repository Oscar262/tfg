package org.iesfm.app.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {

    @Positive
    private Integer id;

    @NotBlank
    private String name;

    @Positive
    @NotNull
    private Integer totalHours;

    @NotNull
    private UserDto userCre;

    private UserDto userMod;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull
    private LocalDate dateCre;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateMod;


    @JsonProperty(namespace = "teacher")
    private Set<UserDto> teacher;


    @JsonProperty(namespace = "student")
    private Set<UserDto> student;


    @JsonProperty(namespace = "classes")
    private Set<ClassDto> classes;


    private Set<AbsenceDto> absence;

    private Double percentage;


}


