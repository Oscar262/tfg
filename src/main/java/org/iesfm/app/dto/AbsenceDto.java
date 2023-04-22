package org.iesfm.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iesfm.app.entity.AbsenceEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbsenceDto {
    @Positive
    @NotNull
    private Integer id;

    @JsonFormat(pattern = "yyyy/MM/dd")
    @NotNull
    private LocalDate date;

    @Positive
    @NotNull
    private Integer numHours;

    public static AbsenceEntity toEntity(AbsenceDto dto) {
        return new AbsenceEntity(
                dto.getId(),
                dto.getDate(),
                dto.getNumHours(),
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static AbsenceDto toDto(AbsenceEntity entity) {
        return new AbsenceDto(
                entity.getId(),
                entity.getDate(),
                entity.getNumHours()
        );
    }
}
