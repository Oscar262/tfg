package org.iesfm.app.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbsenceDto {

    @Positive
    private Integer id;


    @Positive
    @NotNull
    private Integer numHours;


    private UserDto userMod;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateMod;


    @JsonProperty(namespace = "student")
    private UserDto student;


    @JsonProperty(namespace = "teacher")
    private UserDto teacher;


    private SubjectDto subject;


}
