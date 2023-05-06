package org.iesfm.app.dto.mapper;

import org.iesfm.app.dto.AbsenceDto;
import org.iesfm.app.dto.SubjectDto;
import org.iesfm.app.entity.AbsenceEntity;
import org.iesfm.app.entity.SubjectEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubjectMapper {
    public static SubjectDto toDtoInfo(SubjectEntity subject) {
        SubjectDto subjectDto = new SubjectDto();
        Set<AbsenceDto> absences = new HashSet<>();

        for (AbsenceEntity entity : subject.getAbsence()){
            absences.add(AbsenceMapper.toUserDtoToCreateAbsence(entity));
        }

        subjectDto.setId(subject.getId());
        subjectDto.setName(subject.getName());
        subjectDto.setAbsence(absences);

        return subjectDto;
    }

    public static SubjectDto toDtoInfoWithPercentage(SubjectEntity subject, Double percentege) {
        SubjectDto subjectDto = new SubjectDto();

        subjectDto.setId(subject.getId());
        subjectDto.setName(subject.getName());
        subjectDto.setPercentage(percentege);

        return subjectDto;
    }

    public static SubjectEntity toEntityInfo(SubjectDto dto) {
        SubjectEntity subjectEntity = new SubjectEntity();

        subjectEntity.setId(dto.getId());
        subjectEntity.setName(dto.getName());

        return subjectEntity;
    }
}
