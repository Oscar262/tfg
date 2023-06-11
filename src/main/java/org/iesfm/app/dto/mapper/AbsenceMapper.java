package org.iesfm.app.dto.mapper;

import org.iesfm.app.dto.AbsenceDto;
import org.iesfm.app.entity.AbsenceEntity;

/**
 * En esta clase se encuentran todos los metodos que convierten las entidades en dtos para las ausencias
 */
public class AbsenceMapper {

    /**
     * En este metodo se convierte un dto de ausencia a un a entidad de ausencia
     * @param absenceDto es el dto que se debe transformar
     * @return devuelve la entidad con algunos de sus campos
     */
    public static AbsenceEntity toEntity(AbsenceDto absenceDto) {
        AbsenceEntity absenceEntity = new AbsenceEntity();


        absenceEntity.setId(absenceDto.getId());
        absenceEntity.setNumHours(absenceDto.getNumHours());
        absenceEntity.setDate(absenceDto.getDate());
        absenceEntity.setDateMod(absenceDto.getDateMod());

        return absenceEntity;
    }

    public static AbsenceEntity toEntityInfo(AbsenceDto absenceDto) {
        AbsenceEntity absenceEntity = new AbsenceEntity();

        absenceEntity.setId(absenceDto.getId());
        absenceEntity.setNumHours(absenceDto.getNumHours());/*
        absenceEntity.setTeacherCre(UserMapper.toEntityInfo(absenceDto.getTeacher()));

       */
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

    /**
     * En este metodo se transforma una entidad de ausencia a un dto de ausencia
     * @param absence es la entidad que se tiene que trasnformar
     * @return devuelve el dto con algunos de sus campos
     */
    public static AbsenceDto toUserDto(AbsenceEntity absence) {
        AbsenceDto absenceDto = new AbsenceDto();


        absenceDto.setId(absence.getId());
        absenceDto.setNumHours(absence.getNumHours());
        absenceDto.setSubject(SubjectMapper.toDtoInfo(absence.getSubject()));

        absenceDto.setStudent(UserMapper.toDtoLogin(absence.getStudent()));

        absenceDto.setTeacher(UserMapper.toDtoLogin(absence.getTeacherCre()));

        absenceDto.setDate(absence.getDate());
        return absenceDto;
    }

    /**
     * En este metodo se transforma una entidad de ausencia a un dto de ausencia para crear un usuario
     * @param absence es la entidad a trasnformar
     * @return devuelve el dto con algunos de sus campos
     */
    public static AbsenceDto toUserDtoToCreateAbsence(AbsenceEntity absence) {
        AbsenceDto absenceDto = new AbsenceDto();


        absenceDto.setId(absence.getId());
        absenceDto.setNumHours(absence.getNumHours());
        absenceDto.setTeacher(UserMapper.toDtoLogin(absence.getTeacherCre()));

        absenceDto.setDate(absence.getDate());
        return absenceDto;

    }
}
