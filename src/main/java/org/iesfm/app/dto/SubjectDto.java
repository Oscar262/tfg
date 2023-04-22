package org.iesfm.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iesfm.app.entity.AbsenceEntity;
import org.iesfm.app.entity.StudentEntity;
import org.iesfm.app.entity.SubjectEntity;
import org.iesfm.app.entity.TeacherEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {

    @NotBlank
    private String name;

    @Positive
    @NotNull
    private Integer totalHours;

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
    private Set<TeacherEntity> teacher;

    @NotBlank
    private Set<StudentEntity> student;

    @NotBlank
    private List<AbsenceEntity> absence;


//    public static SubjectEntity toEntity(SubjectDto dto) {
//        return new SubjectEntity(
//                dto.getName(),
//                dto.getTotalHours(),
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null
//        );
//    }
//
//    public static SubjectDto toDto(SubjectEntity entity) {
//        return new SubjectDto(
//                entity.getId(),
//                entity.getName(),
//                entity.getTotalHours()
//        );
//    }
}
