package org.iesfm.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iesfm.app.entity.TeacherEntity;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {


    @NotBlank
    private String userCre;

    @NotBlank
    private String userMod;

    @JsonFormat(pattern = "yyyy/MM/dd")
    @NotNull
    private LocalDate dateCre;

    @JsonFormat(pattern = "yyyy/MM/dd")
    @NotNull
    private LocalDate dateMod;

    @NotBlank
    private String email;

    @NotBlank
    private String pass;

    @NotBlank
    private List<TeacherEntity> teachers;

}
