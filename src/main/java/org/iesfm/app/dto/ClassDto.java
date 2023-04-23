package org.iesfm.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iesfm.app.entity.StudentEntity;
import org.iesfm.app.entity.TeacherEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassDto {

    @NotBlank
    private String name;

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
    private List<StudentEntity> students;

    @NotBlank
    private Set<TeacherEntity> teachers;

//    public static ClassEntity toEntity(ClassDto dto) {
//        return new ClassEntity(
//                null,
//                dto.getName(),
//                null,
//                null
//        );
//    }
//
//    public static ClassDto toDto(ClassEntity entity) {
//        return new ClassDto(
//                entity.getId(),
//                entity.getName()
//        );
//    }

}
