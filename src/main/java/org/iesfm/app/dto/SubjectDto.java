package org.iesfm.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iesfm.app.entity.SubjectEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {

    @Positive
    @NotNull
    private Integer id;

    @NotBlank
    private String name;

    @Positive
    @NotNull
    private Integer totalHours;

    public static SubjectEntity toEntity(SubjectDto dto) {
        return new SubjectEntity(
                dto.getId(),
                dto.getName(),
                dto.getTotalHours(),
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static SubjectDto toDto(SubjectEntity entity) {
        return new SubjectDto(
                entity.getId(),
                entity.getName(),
                entity.getTotalHours()
        );
    }
}
