package org.iesfm.app.dto.mapper;

import org.iesfm.app.dto.AbsenceDto;
import org.iesfm.app.entity.AbsenceEntity;

public class AbsenceMapper {

    //    public static AbsenceEntity toEntity(AbsenceDto absenceDto) {
//        AbsenceEntity absenceEntity = new AbsenceEntity();
//
//        absenceEntity.setId(absenceDto.getId());
//        absenceEntity.setNumHours(absenceDto.getNumHours());
//        absenceEntity.setTeacherCre(UserMapper.toEntityInfo(absenceDto.getTeacher()));
//        absenceEntity.setUserMod(UserMapper.toEntityInfo(absenceDto.getUserMod()));
//        absenceEntity.setDate(absenceDto.getDate());
//        absenceEntity.setDateMod(absenceDto.getDateMod());
//        absenceEntity.setStudent(UserMapper.toEntityInfo(absenceDto.getStudent()));
//        absenceEntity.setSubject(SubjectMapper.toEntity(absenceDto.getSubject()));
//
//        return absenceEntity;
//    }
//
    public static AbsenceEntity toEntityInfo(AbsenceDto absenceDto) {
        AbsenceEntity absenceEntity = new AbsenceEntity();

        absenceEntity.setId(absenceDto.getId());
        absenceEntity.setNumHours(absenceDto.getNumHours());
        absenceEntity.setTeacherCre(UserMapper.toEntityInfo(absenceDto.getTeacher()));
        absenceEntity.setDate(absenceDto.getDate());
        absenceEntity.setStudent(UserMapper.toEntityInfo(absenceDto.getStudent()));
        absenceEntity.setSubject(SubjectMapper.toEntityInfo(absenceDto.getSubject()));

        return absenceEntity;
    }
//
//    public static AbsenceEntity toEntityForSubject(AbsenceDto absenceDto){
//        AbsenceEntity absenceEntity = new AbsenceEntity();
//
//        absenceEntity.setId(absenceDto.getId());
//        absenceEntity.setNumHours(absenceDto.getNumHours());
//        absenceEntity.setTeacherCre(UserMapper.toEntityInfo(absenceDto.getTeacher()));
//        absenceEntity.setDate(absenceDto.getDate());
//        absenceEntity.setStudent(UserMapper.toEntityInfo(absenceDto.getStudent()));
//
//        return absenceEntity;
//    }

    public static AbsenceDto toUserDto(AbsenceEntity absence) {
        AbsenceDto absenceDto = new AbsenceDto();


        absenceDto.setId(absence.getId());
        absenceDto.setNumHours(absence.getNumHours());
        absenceDto.setSubject(SubjectMapper.toDtoInfo(absence.getSubject()));
        absenceDto.setTeacher(UserMapper.toDtoInfo(absence.getTeacherCre()));
        absenceDto.setDate(absence.getDate());
        return absenceDto;
    }


}
