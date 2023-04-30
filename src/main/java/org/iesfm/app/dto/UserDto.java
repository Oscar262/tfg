package org.iesfm.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iesfm.app.entity.AbsenceEntity;
import org.iesfm.app.entity.SubjectEntity;
import org.iesfm.app.entity.UserClassSubject;

import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {


    @Positive
    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String firstSurname;


    private String secondSurname;

    @NotNull
    @Positive
    private Integer userCre;

    @Positive
    private Integer userMod;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateCre;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateMod;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String pass;

    @NotNull
    private RoleDto role;

    @NotNull
    private List<AbsenceDto> absenceList;

    @NotNull
    private List<SubjectDto> subjectList;

    @NotNull
    private List<ClassDto> classList;

}
