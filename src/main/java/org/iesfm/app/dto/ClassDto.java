package org.iesfm.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iesfm.app.entity.ClassEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassDto {

    @Positive
    @NotNull
    private Integer id;

    @NotBlank
    private String name;

    public static ClassEntity toEntity(ClassDto dto) {
        return new ClassEntity(
                dto.getId(),
                dto.getName(),
                null,
                null
        );
    }

    public static ClassDto toDto(ClassEntity entity) {
        return new ClassDto(
                entity.getId(),
                entity.getName()
        );
    }

}
