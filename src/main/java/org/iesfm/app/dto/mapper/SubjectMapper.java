package org.iesfm.app.dto.mapper;

import org.iesfm.app.dto.AbsenceDto;
import org.iesfm.app.dto.ClassDto;
import org.iesfm.app.dto.SubjectDto;
import org.iesfm.app.entity.AbsenceEntity;
import org.iesfm.app.entity.SubjectEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * En esta clase se encuentran todos los metodos que convierten las entidades en dtos para las asignaturas
 */
public class SubjectMapper {

    /**
     * En este metodo se transforma una entidad de asignatura en un dto de asignatura
     * @param subject es la entidad a transformar
     * @return devuelve el dto de la asignatura
     */
    public static SubjectDto toDtoInfo(SubjectEntity subject) {
        SubjectDto subjectDto = new SubjectDto();
        Set<AbsenceDto> absences = new HashSet<>();

        for (AbsenceEntity entity : subject.getAbsence()) {
            absences.add(AbsenceMapper.toUserDtoToCreateAbsence(entity));
        }

        subjectDto.setId(subject.getId());
        subjectDto.setName(subject.getName());
        subjectDto.setAbsence(absences);

        return subjectDto;
    }

    /**
     * En este metodo se transforma una entidad de asignatura en un dto de asignatura
     * @param subject es la entidad a transformar
     * @param percentege es el porcentaje de faltas que se pueden tener para la asignatura
     * @return devuelve el dto de la asignatura con el porcentaje
     */
    public static SubjectDto toDtoInfoWithPercentage(SubjectEntity subject, Double percentege) {
        SubjectDto subjectDto = new SubjectDto();

        subjectDto.setId(subject.getId());
        subjectDto.setName(subject.getName());
        subjectDto.setPercentage(percentege);

        return subjectDto;
    }

    /**
     * En este metodo se transforma un dto de asignatura en una entidad de asignatura
     * @param dto es el dto que se transformara
     * @return devuelve la entidad de la asignatura
     */
    public static SubjectEntity toEntityInfo(SubjectDto dto) {
        SubjectEntity subjectEntity = new SubjectEntity();

        subjectEntity.setId(dto.getId());
        subjectEntity.setName(dto.getName());

        return subjectEntity;
    }

    /**
     * En este metodo se transforma un dto de asignatura en una entidad de asignatura
     * @param subjectDto es el dto a transformar
     * @param now es la fecha en la que se usa este metodo
     * @return devuelve la entidad de la asignatura
     */
    public static SubjectEntity toEntity(SubjectDto subjectDto, LocalDate now) {
        SubjectEntity entity = new SubjectEntity();

        entity.setId(subjectDto.getId());
        entity.setName(subjectDto.getName());
        entity.setTotalHours(subjectDto.getTotalHours());
        entity.setDateCre(now);

        return entity;
    }

    /**
     * En este metodo se transforma una entidad de asignatura en un dto de asignatura
     * @param subject es la entidad a transformar
     * @return devuelve el dto de la asignatura
     */
    public static SubjectDto toDtoName(SubjectEntity subject) {
        SubjectDto subjectDto = new SubjectDto();

        subjectDto.setId(subject.getId());
        subjectDto.setName(subject.getName());

        return subjectDto;
    }

    /**
     * En este metodo se transforma una lista de dtos de asignaturas en un conjunto de entidades de asignaturas
     * @param subjectList es la lista a transformar
     * @return devuelve el conjunto de entidades de asignaturas
     */
    public static Set<SubjectEntity> toEntityList(List<SubjectDto> subjectList) {

        List<SubjectEntity> listEntity = new ArrayList<>();

        for (SubjectDto dto:subjectList) {
            listEntity.add(toEntity(dto,LocalDate.now()));
        }
        return new HashSet<>(listEntity);


    }
}
