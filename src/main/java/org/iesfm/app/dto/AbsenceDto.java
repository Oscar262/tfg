package org.iesfm.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iesfm.app.entity.StudentEntity;
import org.iesfm.app.entity.SubjectEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbsenceDto {

    @JsonFormat(pattern = "yyyy/MM/dd")
    @NotNull
    private LocalDate date;

    @Positive
    @NotNull
    private Integer numHours;

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
    private StudentEntity student;

    @NotBlank
    private SubjectEntity subject;

//    public static AbsenceEntity toEntity(AbsenceDto dto) {
//        return new AbsenceEntity(
//                null,
//                dto.getDate(),
//                dto.getNumHours(),
//                null,
//                null,
//                null,
//                null,
//                null,
//                null
//        );
//    }
//
//    public static AbsenceDto toDto(AbsenceEntity entity) {
//        return new AbsenceDto(
//                entity.getId(),
//                entity.getDate(),
//                entity.getNumHours()
//        );
//    }
}
