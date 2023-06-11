package org.iesfm.app.dao;

import org.iesfm.app.entity.AbsenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * En esta clase se controla el acceso en base de datos para las ausencias
 */
@Repository
public interface AbsenceDao extends JpaRepository<AbsenceEntity, Integer> {

    /**
     * En este metodo se busca en base de datos una lista de ausencias
     * @param date es la fecha que tienen que tener las ausencias en la base de datos
     * @param id es el id del usuario al que pertenecen las ausencias
     * @return devuelve la lista con las ausencias
     */
    List<AbsenceEntity> findByDateAndStudent_Id(LocalDate date, Integer id);

    /**
     * En este metodo se busca en la base de datos una lista de ausencias, que se ordenaran por el id del estudiante de
     * forma ascendente, y la fecha de forma descendiente
     * @param teacherId es el id del profesor que ha generado las ausencias a buscar
     * @param subjectId es el id de la asignatura para la que se ha generado la ausencia
     * @return devuelve la lista de ausencias que cumplan con los campos anteriores
     */
    List<AbsenceEntity> findByTeacherCre_IdAndSubject_IdOrderByStudent_IdAscDateDesc(Integer teacherId, Integer subjectId);


}
