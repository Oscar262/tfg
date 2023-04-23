package org.iesfm.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.iesfm.app.entity.ClassEntity;
import org.iesfm.app.entity.SubjectEntity;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class TeacherDto {

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
    @JsonProperty("Usuario de creacion")
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
    @JsonProperty("Is Admin?")
    private Boolean isAdmin;

    @NotNull
    @JsonProperty("Classes")
    private Set<ClassEntity> classEntities;

    @NotBlank
    @JsonProperty("Subjects")
    private List<SubjectEntity> subjectList;
}
