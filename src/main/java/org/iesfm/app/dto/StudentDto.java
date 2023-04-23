package org.iesfm.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iesfm.app.entity.AbsenceEntity;
import org.iesfm.app.entity.ClassEntity;
import org.iesfm.app.entity.SubjectEntity;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {


    @NotBlank
    @JsonProperty("Name")
    private String name;

    @NotBlank
    @JsonProperty("First Surname")
    private String firstSurname;

    @NotBlank
    @JsonProperty("Second Surname")
    private String secondSurname;

    @NotBlank
    @JsonProperty("User Created")
    private String userCre;

    @NotBlank
    @JsonProperty("Usuario de modificacion")
    private String userMod;

    @NotBlank
    @JsonProperty("Fecha de cracion")
    private LocalDate dateCre;

    @NotBlank
    @JsonProperty("Fecha de modificacion")
    private LocalDate dateMod;

    @NotBlank
    @JsonProperty("Email")
    private String email;

    @NotBlank
    @JsonProperty("Password")
    private String pass;


    @NotBlank
    @JsonProperty("Subjects")
    private Set<SubjectEntity> subject;

    @NotBlank
    @JsonProperty("Absences")
    private List<AbsenceEntity> absenceList;

    @NotBlank
    @JsonProperty("Class")
    private ClassEntity classEntity;
}
