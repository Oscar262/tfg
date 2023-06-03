package org.iesfm.app.dao;

import org.iesfm.app.entity.AbsenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AbsenceDao extends JpaRepository<AbsenceEntity, Integer> {

    List<AbsenceEntity> findBySubject_Name(String name);

    List<AbsenceEntity> findByStudent_NameAndStudent_FirstSurname(String name, String firstSurname);

    List<AbsenceEntity> findByStudent_Name(String name);

    @Query("select a from AbsenceEntity a where a.student.firstSurname = ?1")
    List<AbsenceEntity> findByStudent_FirstSurname(String firstSurname);

    List<AbsenceEntity> findBySubject_NameAndStudent_NameAndStudent_FirstSurname(String name, String name1, String firstSurname);

    List<AbsenceEntity> findByDateAndStudent_Id(LocalDate date, Integer id);

    List<AbsenceEntity> findByTeacherCre_IdAndSubject_IdOrderByStudent_IdAsc(Integer teacherId, Integer subjectId);

    List<AbsenceEntity> findByTeacherCre_IdAndSubject_IdOrderByStudent_IdAscDateDesc(Integer teacherId, Integer subjectId);


}
