package org.iesfm.app.service;

import lombok.SneakyThrows;
import org.iesfm.app.entity.SubjectEntity;
import org.iesfm.app.exceptions.IncorrectUserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
public class SubjectServiceTest {


    @Autowired
    private SubjectService subjectService;
    @Autowired
    private UserService userService;

    @Test
    public void getSubjectTest() {
        Assertions.assertNotNull(subjectService.getSubject(1));
    }

    @Test
    public void getSubjectTestFail() {
        Assertions.assertNull(subjectService.getSubject(900));
    }

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

    @Test
    public void findAllSubjectsTest() {
        List<SubjectEntity> subjectEntities = subjectService.findAllSubjects(3);
        Assertions.assertNotNull(subjectEntities);
    }

}
