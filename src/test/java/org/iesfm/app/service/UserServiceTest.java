package org.iesfm.app.service;


import lombok.SneakyThrows;
import org.iesfm.app.dto.SubjectDto;
import org.iesfm.app.dto.mapper.SubjectMapper;
import org.iesfm.app.entity.ClassEntity;
import org.iesfm.app.entity.RoleEntity;
import org.iesfm.app.entity.SubjectEntity;
import org.iesfm.app.entity.UserEntity;
import org.iesfm.app.exceptions.ClassListException;
import org.iesfm.app.exceptions.ClassListExceptionStudent;
import org.iesfm.app.exceptions.EmptytListException;
import org.iesfm.app.exceptions.IncorrectUserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * En esta clase se encuentran todos los test que se van a hacer para la clase "UserService" (se deben cambiar los valores
 *  *  para que coincidan con los datos correctos)
 */
@SpringBootTest
@Transactional
public class UserServiceTest {


    @Autowired
    private AbsenceService absenceService;
    @Autowired
    private UserService userService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ClassService classService;

    /**
     * En este test se comprueba que metiendo todos los datos correctamente el resultado obtenido es el deseado para el
     * metodo "getUserLogin"
     */
    @SneakyThrows
    @Test
    public void getUserLoginTest() {
        UserEntity user = (userService.getUserLogin("s1@gmail.com", "pass"));
        UserEntity teacher = (userService.getUserLogin("t@gmail.com", "pass"));
        UserEntity admin = (userService.getUserLogin("a@gmail.com", "pass"));

        Assertions.assertEquals(user.getRole().getId(), 1);
        Assertions.assertEquals(teacher.getRole().getId(), 2);
        Assertions.assertEquals(admin.getRole().getId(), 3);
    }

    /**
     * En este test se comprueba que metiendo todos los datos correctamente el resultado obtenido es el deseado para el
     * metodo "findAllStudents"
     */
    @SneakyThrows
    @Test
    public void findAllStudentsTest() {
        Assertions.assertNotNull(userService.findAllStudents(1, 1, 1));
    }

    /**
     * En este test se comprueba que metiendo datos erroneos (se introducen datos que no existe, o que no tienen relaciones)
     * el resultado obtenido es el deseado para el metodo "findAllStudents"
     */
    @SneakyThrows
    @Test
    public void findAllStudentsTestFail() {

        Assertions.assertThrows(EmptytListException.class, () -> {
            userService.findAllStudents(6, 1, 1);
        });
    }

    /**
     * En este test se comprueba que metiendo todos los datos correctamente el resultado obtenido es el deseado para el
     * metodo "getUser"
     */
    @Test
    public void getUserTest() {
        Assertions.assertNotNull(userService.getUser(1));
    }

    /**
     * En este test se comprueba que metiendo un valor incorrecto para el id de usuario (se introduce uno que no existe)
     * el resultado obtenido es el deseado para el metodo "getUser"
     */
    @Test
    public void getUserTestFail() {

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            userService.getUser(18);
        });
    }

    /**
     * En este test se comprueba que metiendo todos los datos correctamente el resultado obtenido es el deseado para el
     * metodo "getAbsencePercentage"
     */
    @Test
    public void getAbsencePercentageTest() {
        Set<SubjectEntity> subjectEntities = userService.getUser(1).getSubjectList();
        List<SubjectDto> dtos = new ArrayList<>();
        for (SubjectEntity subject : subjectEntities) {
            SubjectMapper.toDtoInfo(subject);
        }
        userService.getAbsencePercentage(userService.getUser(1), dtos);
        for (SubjectDto dto : dtos) {
            Assertions.assertNotNull(dto.getPercentage());
        }
    }

    /**
     * En este test se comprueba que metiendo todos los datos correctamente el resultado obtenido es el deseado para el
     * metodo "userIsAdmin"
     */
    @Test
    public void userIsAdminTest() {
        Assertions.assertTrue(userService.userIsAdmin(userService.getUser(4)));
    }

    /**
     * En este test se comprueba que metiendo un valor incorrecto para el id user (se introduce uno que no es admin)
     * el resultado obtenido es el deseado para el metodo "userIsAdmin"
     */
    @Test
    public void userIsAdminTestFail() {
        Assertions.assertFalse(userService.userIsAdmin(userService.getUser(1)));
    }

    /**
     * En este test se comprueba que metiendo todos los datos correctamente el resultado obtenido es el deseado para el
     * metodo "addUser"
     */
    @SneakyThrows
    @Test
    public void addUserTest() {
        UserEntity entity = new UserEntity();
        Set<ClassEntity> classEntities = new HashSet<>();
        classEntities.add(classService.findByid(2));
        RoleEntity role = new RoleEntity();
        role.setId(1);
        role.setName("Student");

        entity.setEmail("prueba@email.app");
        entity.setPass("123");
        entity.setName("Prueba");
        entity.setFirstSurname("Apellido");
        entity.setDateCre(LocalDate.now());
        entity.setUsuCre(4);
        entity.setSubjectList(new HashSet<>());
        entity.setRole(role);
        entity.setClassEntities(classEntities);
        userService.addUser(entity, 4);
        Assertions.assertNotNull(entity.getId());
    }

    /**
     * En este test se comprueba que metiendo todos los datos correctamente, excepto la lista de clases (no esta vacia)
     * el resultado obtenido es el deseado para el metodo "addUser"
     */
    @Test
    public void addUserTestFailListClassAdmin() {
        Set<ClassEntity> classEntities = new HashSet<>();
        classEntities.add(classService.findByid(2));
        UserEntity entity = new UserEntity();
        RoleEntity role = new RoleEntity();
        role.setId(3);
        role.setName("Admin");

        entity.setEmail("prueba@email.app");
        entity.setPass("123");
        entity.setName("Prueba");
        entity.setFirstSurname("Apellido");
        entity.setDateCre(LocalDate.now());
        entity.setUsuCre(4);
        entity.setClassEntities(classEntities);
        entity.setRole(role);
        Assertions.assertThrows(ClassListException.class, () -> {
            userService.addUser(entity, 4);
        });
    }

    /**
     * En este test se comprueba que metiendo todos los datos correctamente, excepto la lista de clases (hay mas de una)
     * el resultado obtenido es el deseado para el metodo "addUser"
     */
    @Test
    public void addUserTestFailListClassStudent() {
        Set<ClassEntity> classEntities = new HashSet<>();
        classEntities.add(classService.findByid(1));
        classEntities.add(classService.findByid(2));
        UserEntity entity = new UserEntity();
        RoleEntity role = new RoleEntity();
        role.setId(1);
        role.setName("Student");

        entity.setEmail("prueba@email.app");
        entity.setPass("123");
        entity.setName("Prueba");
        entity.setFirstSurname("Apellido");
        entity.setDateCre(LocalDate.now());
        entity.setUsuCre(4);
        entity.setClassEntities(classEntities);
        entity.setRole(role);
        Assertions.assertThrows(ClassListExceptionStudent.class, () -> {
            userService.addUser(entity, 4);
        });
    }

    /**
     * En este test se comprueba que metiendo todos los datos correctamente, excepto el administrador (no es admin)
     * el resultado obtenido es el deseado para el metodo "addUser"
     */
    @Test
    public void addUserTestFailAdmin() {
        UserEntity entity = new UserEntity();
        RoleEntity role = new RoleEntity();
        role.setId(1);
        role.setName("Student");

        entity.setEmail("prueba@email.app");
        entity.setPass("123");
        entity.setName("Prueba");
        entity.setFirstSurname("Apellido");
        entity.setDateCre(LocalDate.now());
        entity.setUsuCre(2);
        entity.setRole(role);
        entity.setClassEntities(new HashSet<>());
        Assertions.assertThrows(IncorrectUserException.class, () -> {
            userService.addUser(entity, 2);
        });
    }

    /**
     * En este test se comprueba que metiendo todos los datos correctamente, excepto el email (ya existe)
     * el resultado obtenido es el deseado para el metodo "addUser"
     */
    @Test
    public void addUserTestFailEmail() {
        UserEntity entity = new UserEntity();
        RoleEntity role = new RoleEntity();
        role.setId(1);
        role.setName("Student");

        entity.setEmail("s1@gmail.com");
        entity.setPass("123");
        entity.setName("Prueba");
        entity.setFirstSurname("Apellido");
        entity.setDateCre(LocalDate.now());
        entity.setUsuCre(4);
        entity.setClassEntities(new HashSet<>());
        entity.setRole(role);
        Assertions.assertThrows(EntityExistsException.class, () -> {
            userService.addUser(entity, 4);
        });
    }
}
