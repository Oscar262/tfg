package org.iesfm.app.service;

import lombok.SneakyThrows;
import org.iesfm.app.entity.AbsenceEntity;
import org.iesfm.app.exceptions.IncorrectDataExpected;
import org.iesfm.app.exceptions.IncorrectDateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;


@SpringBootTest
@Transactional
public class AbsenceServiceTest {

    private MockMvc mockMvc;

    @Autowired
    private AbsenceService absenceService;
    @Autowired
    private UserService userService;
    @Autowired
    private SubjectService subjectService;

    @SneakyThrows
    @Test
    public void addAbsenceTest() {
        AbsenceEntity absence = absence(2);
        absenceService.addAbsence(absence, 2, 1, 3);

        Assertions.assertNotNull(absence.getId());
    }

    @SneakyThrows
    @Test
    public void addAbsenceTestFailDateWeekEnd() {
        AbsenceEntity absence = absence(2);
        absence.setDate(LocalDate.of(2023, 06, 03));
        Assertions.assertThrows(IncorrectDateException.class, () -> {
            absenceService.addAbsence(absence, 2, 1, 3);
        });
    }

    @SneakyThrows
    @Test
    public void addAbsenceTestFailDateJuly() {
        AbsenceEntity absence = absence(2);
        absence.setDate(LocalDate.of(2023, 07, 03));
        Assertions.assertThrows(IncorrectDateException.class, () -> {
            absenceService.addAbsence(absence, 2, 1, 3);
        });
    }

    @SneakyThrows
    @Test
    public void addAbsenceTestFailDateAugust() {
        AbsenceEntity absence = absence(2);
        absence.setDate(LocalDate.of(2023, 07, 03));
        Assertions.assertThrows(IncorrectDateException.class, () -> {
            absenceService.addAbsence(absence, 2, 1, 3);
        });
    }

    @SneakyThrows
    @Test
    public void addAbsenceTestFailTeacherNotFound() {
        AbsenceEntity absence = absence(2);
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            absenceService.addAbsence(absence, 2, 1, 320);
        });
    }

    @SneakyThrows
    @Test
    public void addAbsenceTestFailStudentNotFound() {
        AbsenceEntity absence = absence(2);
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            absenceService.addAbsence(absence, 2, 180, 20);
        });
    }

    @SneakyThrows
    @Test
    public void addAbsenceTestFailIncorrectData() {
        AbsenceEntity absence = absence(2);
        absence.setNumHours(8);
        Assertions.assertThrows(IncorrectDataExpected.class, () -> {
            absenceService.addAbsence(absence, 2, 1, 3);
        });
    }

    @Test
    public void getAllStudentsByTeacherAndSubjectTest() {
        List<AbsenceEntity> absenceEntities = absenceService.findAllAbsences(3, 2);
        Assertions.assertNotNull(absenceEntities);
    }


    private AbsenceEntity absence(int idSubject) {
        AbsenceEntity entity = new AbsenceEntity();

        entity.setSubject(subjectService.getSubject(idSubject));
        entity.setDate(LocalDate.of(2023, 06, 02));
        entity.setNumHours(2);
        return entity;
    }
}
