package org.iesfm.app.service;

import lombok.SneakyThrows;
import org.iesfm.app.entity.AbsenceEntity;
import org.iesfm.app.exceptions.IncorrectDataExpected;
import org.iesfm.app.exceptions.IncorrectDateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

/**
 * En esta clase se encuentran todos los test que se van a hacer para la clase "AbsenceService" (se deben cambiar los valores
 *  *  para que coincidan con los datos correctos)
 */
@SpringBootTest
@Transactional
public class AbsenceServiceTest {

    @Autowired
    private AbsenceService absenceService;
    @Autowired
    private UserService userService;
    @Autowired
    private SubjectService subjectService;

    /**
     * En este test se comprueba que metiendo todos los datos correctamente el resultado obtenido es el deseado para el
     * metodo "addAbsence"
     */
    @SneakyThrows
    @Test
    public void addAbsenceTest() {
        AbsenceEntity absence = absence(2);
        absenceService.addAbsence(absence, 2, 1, 3);

        Assertions.assertNotNull(absence.getId());
    }

    /**
     * En este test se comprueba que metiendo todos los datos correctamente, excepto la fecha (se introduce en fin de semana)
     * el resultado obtenido es el deseado para el metodo "addAbsence"
     */
    @SneakyThrows
    @Test
    public void addAbsenceTestFailDateWeekEnd() {
        AbsenceEntity absence = absence(2);
        absence.setDate(LocalDate.of(2023, 06, 03));
        Assertions.assertThrows(IncorrectDateException.class, () -> {
            absenceService.addAbsence(absence, 2, 1, 3);
        });
    }
    /**
     * En este test se comprueba que metiendo todos los datos correctamente, excepto la fecha (se introduce en julio)
     * el resultado obtenido es el deseado para el metodo "addAbsence"
     */
    @SneakyThrows
    @Test
    public void addAbsenceTestFailDateJuly() {
        AbsenceEntity absence = absence(2);
        absence.setDate(LocalDate.of(2023, 07, 03));
        Assertions.assertThrows(IncorrectDateException.class, () -> {
            absenceService.addAbsence(absence, 2, 1, 3);
        });
    }

    /**
     * En este test se comprueba que metiendo todos los datos correctamente, excepto la fecha (se introduce en agosto)
     * el resultado obtenido es el deseado para el metodo "addAbsence"
     */
    @SneakyThrows
    @Test
    public void addAbsenceTestFailDateAugust() {
        AbsenceEntity absence = absence(2);
        absence.setDate(LocalDate.of(2023, 07, 03));
        Assertions.assertThrows(IncorrectDateException.class, () -> {
            absenceService.addAbsence(absence, 2, 1, 3);
        });
    }

    /**
     * En este test se comprueba que metiendo todos los datos correctamente, excepto el profesor (se introduce uno que no existe)
     * el resultado obtenido es el deseado para el metodo "addAbsence"
     */
    @SneakyThrows
    @Test
    public void addAbsenceTestFailTeacherNotFound() {
        AbsenceEntity absence = absence(2);
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            absenceService.addAbsence(absence, 2, 1, 320);
        });
    }

    /**
     * En este test se comprueba que metiendo todos los datos correctamente, excepto el estudiante (se introduce uno que no existe)
     * el resultado obtenido es el deseado para el metodo "addAbsence"
     */
    @SneakyThrows
    @Test
    public void addAbsenceTestFailStudentNotFound() {
        AbsenceEntity absence = absence(2);
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            absenceService.addAbsence(absence, 2, 180, 20);
        });
    }

    /**
     * En este test se comprueba que metiendo todos los datos correctamente, excepto el numero de horas (se introduce uno
     * que supere las permitidas) el resultado obtenido es el deseado para el metodo "addAbsence"
     */
    @SneakyThrows
    @Test
    public void addAbsenceTestFailIncorrectData() {
        AbsenceEntity absence = absence(2);
        absence.setNumHours(8);
        Assertions.assertThrows(IncorrectDataExpected.class, () -> {
            absenceService.addAbsence(absence, 2, 1, 3);
        });
    }

    /**
     * En este test se comprueba que metiendo todos los datos correctamente el resultado obtenido es el deseado para el
     * metodo "getAllStudentsByTeacherAndSubject"
     */
    @Test
    public void getAllStudentsByTeacherAndSubjectTest() {
        List<AbsenceEntity> absenceEntities = absenceService.findAllAbsences(3, 2);
        Assertions.assertNotNull(absenceEntities);
    }

    /**
     * Este metodo se utilizara para crear una ausencia que se utilizara en el resto de test
     * @param idSubject es el id de la asignatura a la que se creara la ausencia
     * @return devuelve la ausencia
     */
    private AbsenceEntity absence(int idSubject) {
        AbsenceEntity entity = new AbsenceEntity();

        entity.setSubject(subjectService.getSubject(idSubject));
        entity.setDate(LocalDate.of(2023, 06, 02));
        entity.setNumHours(2);
        return entity;
    }
}
