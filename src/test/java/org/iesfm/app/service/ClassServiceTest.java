package org.iesfm.app.service;


import lombok.SneakyThrows;
import org.iesfm.app.entity.ClassEntity;
import org.iesfm.app.exceptions.IncorrectUserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Transactional
public class ClassServiceTest {


    @Autowired
    private ClassService classService;
    @Autowired
    private UserService userService;


    @Test
    public void findAllclassesTest() {
        Assertions.assertNotNull(classService.findAllclasses(1));
    }

    @SneakyThrows
    @Test
    public void addClassesTest() {
        ClassEntity classEntity = new ClassEntity();
        classEntity.setName("prueba");
        classEntity.setUserCre(userService.getUser(4));
        classEntity.setDateCre(LocalDate.now());
        classService.addClass(classEntity, 4);

        Assertions.assertNotNull(classEntity.getId());
    }

    @SneakyThrows
    @Test
    public void addClassesTestFailAdmin() {
        ClassEntity classEntity = new ClassEntity();
        classEntity.setName("prueba");
        classEntity.setUserCre(userService.getUser(3));
        classEntity.setDateCre(LocalDate.now());
        Assertions.assertThrows(IncorrectUserException.class, () -> {
            classService.addClass(classEntity, 3);
        });
    }

    @Test
    public void findAllClassesByTeacherTest() {
        Assertions.assertNotNull(classService.findAllClassesByTeacher(4));
    }
}
