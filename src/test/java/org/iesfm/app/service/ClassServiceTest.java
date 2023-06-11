package org.iesfm.app.service;


import lombok.SneakyThrows;
import org.iesfm.app.entity.ClassEntity;
import org.iesfm.app.exceptions.IncorrectUserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * En esta clase se encuentran todos los test que se van a hacer para la clase "ClassService" (se deben cambiar los valores
 *  *  para que coincidan con los datos correctos)
 */
@SpringBootTest
@Transactional
public class ClassServiceTest {


    @Autowired
    private ClassService classService;
    @Autowired
    private UserService userService;


    /**
     * En este test se comprueba que metiendo todos los datos correctamente el resultado obtenido es el deseado para el
     * metodo "findAllClasses"
     */
    @Test
    public void findAllClassesTest() {
        Assertions.assertNotNull(classService.findAllclasses(1));
    }

    /**
     * En este test se comprueba que metiendo todos los datos correctamente el resultado obtenido es el deseado para el
     * metodo "addClasses"
     */
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

    /**
     * En este test se comprueba que metiendo todos los datos correctamente, excepto el administrador (no es admin)
     * el resultado obtenido es el deseado para el metodo "addClasses"
     */
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

    /**
     * En este test se comprueba que metiendo todos los datos correctamente el resultado obtenido es el deseado para el
     * metodo "findAllClassesByTeacher"
     */
    @Test
    public void findAllClassesByTeacherTest() {
        Assertions.assertNotNull(classService.findAllClassesByTeacher(4));
    }
}
