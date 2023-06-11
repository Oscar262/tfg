package org.iesfm.app.service;

import lombok.SneakyThrows;
import org.iesfm.app.entity.SubjectEntity;
import org.iesfm.app.exceptions.IncorrectUserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * En esta clase se encuentran todos los test que se van a hacer para la clase "SubjectService (se deben cambiar los valores
 *  para que coincidan con los datos correctos)
 */
@SpringBootTest
@Transactional
public class SubjectServiceTest {


    @Autowired
    private SubjectService subjectService;
    @Autowired
    private UserService userService;

    /**
     * En este test se comprueba que metiendo todos los datos correctamente el resultado obtenido es el deseado para el
     * metodo "getSubject"
     */
    @Test
    public void getSubjectTest() {
        Assertions.assertNotNull(subjectService.getSubject(1));
    }

    /**
     * En este test se comprueba que metiendo un id incorrecto para "subject" (se introduce uno que no existe)
     * el resultado obtenido es el deseado para el metodo "getSubject"
     */
    @Test
    public void getSubjectTestFail() {
        Assertions.assertNull(subjectService.getSubject(900));
    }

    /**
     * En este test se comprueba que metiendo todos los datos correctamente el resultado obtenido es el deseado para el
     * metodo "addSubject"
     */
    @SneakyThrows
    @Test
    public void addSubjectTest() {
        SubjectEntity subject = new SubjectEntity();
        subject.setUserCre(userService.getUser(4));
        subject.setName("prueba");
        subject.setDateCre(LocalDate.now());
        subject.setTotalHours(150);
        subjectService.addSubject(subject, 4);
    }

    /**
     * En este test se comprueba que metiendo todos los datos correctamente, excepto el admin (se introduce uno que no es admin)
     * el resultado obtenido es el deseado para el metodo "addSubject"
     */
    @SneakyThrows
    @Test
    public void addSubjectTestFaillAdmin() {
        SubjectEntity subject = new SubjectEntity();
        subject.setUserCre(userService.getUser(4));
        subject.setName("prueba");
        subject.setDateCre(LocalDate.now());
        subject.setTotalHours(150);
        Assertions.assertThrows(IncorrectUserException.class, () -> {
            subjectService.addSubject(subject, 2);
        });
    }

    /**
     * En este test se comprueba que metiendo todos los datos correctamente el resultado obtenido es el deseado para el
     * metodo "findAllSubjects"
     */
    @Test
    public void findAllSubjectsTest() {
        List<SubjectEntity> subjectEntities = subjectService.findAllSubjects(3);
        Assertions.assertNotNull(subjectEntities);
    }

}
