package org.iesfm.app.service;

import org.iesfm.app.configuration.Config;
import org.iesfm.app.dao.UserDao;
import org.iesfm.app.dto.SubjectDto;
import org.iesfm.app.dto.mapper.SubjectMapper;
import org.iesfm.app.entity.AbsenceEntity;
import org.iesfm.app.entity.SubjectEntity;
import org.iesfm.app.entity.UserEntity;
import org.iesfm.app.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Esta clase se encuentran los metodos relacionados con el servicio de la aplicacion que afectan a los usuarios
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    /**
     * En este metodo se comprueba el role del usuario
     * @param email es el email que tiene que tener el usuario
     * @param pass es la contraseña que tiene que tener el usuario
     * @return devuelve al usuario que contenga los dos campos anteriores
     * @throws UserNotFoundException se lanzara si el usuario no existe en la base de datos
     */
    public UserEntity getUserLogin(String email, String pass) throws UserNotFoundException {

        UserEntity entity = userDao.findByEmailAndPass(email, pass);
        if (entity == null) {
            throw new UserNotFoundException();
        }
        return entity;

    }

    /**
     * Este metodo buscara una lista de usuarios en la base de datos
     * @param classId es el id de la clase con la que deben estar relacionados los usuarios
     * @param subjectId es el id de la asignatura con la que deben estar relacionados los usuarios
     * @param role es el id del role con la que deben estar relacionados los usuarios
     * @return devuelve una lista con los usuarios que cumplan las condiciones anteriores
     * @throws EmptytListException se lanzara esta excepcion si ningun usuario cumple con las condiciones anteriores
     */
    public List<UserEntity> findAllStudents(Integer classId, Integer subjectId, Integer role) throws EmptytListException {

        if (userDao.findByRole_IdAndSubjectList_IdAndClassEntities_Id(role, subjectId, classId).isEmpty()) {
            throw new EmptytListException();
        } else return userDao.findByRole_IdAndSubjectList_IdAndClassEntities_Id(role, subjectId, classId);


    }

    /**
     * En este metodo se buscara un usuario en la base de datos
     * @param idUser es el id del usuario a buscar
     * @return devuelve el usuario encontrado en la base de datos
     */
    public UserEntity getUser(int idUser) {
        return userDao.findById(idUser).orElseThrow(EntityNotFoundException::new);
    }

    /**
     * En este metodo se comprueba el porcentaje de faltas que tiene un usuario para cada asignatura que cursa
     * @param entity es el usuario para el que se comprueba el porcentaje
     * @param subjectDtos es la lista de asignaturas a las que se le asignara el porcentaje
     */
    public void getAbsencePercentage(UserEntity entity, List<SubjectDto> subjectDtos) {
        for (SubjectEntity subject : entity.getSubjectList()) {
            Double countAbsences = 0.00;
            Double percentage = 0.00;


            for (AbsenceEntity absence : entity.getAbsenceList()) {
                if (absence.getSubject().getId().equals(subject.getId()) && absence.getStudent().equals(entity)) {
                    countAbsences += absence.getNumHours();
                }


            }
            percentage = (countAbsences * 100) / subject.getTotalHours();
            SubjectDto subjectDto = SubjectMapper.toDtoInfoWithPercentage(subject, percentage);
            subjectDtos.add(subjectDto);
        }

    }

    /**
     * En este metodo se comprueba si un usuario es administrador o no
     * @param user es el usuario que se va a comprobar
     * @return devuelve false si no es administrador, o true en caso de que si lo sea
     */
    public boolean userIsAdmin(UserEntity user) {
        return user.getRole().getName().equalsIgnoreCase("Admin");

    }

    /**
     * En este metodo se comprueba si el usuario existe en la base de datos comprobando su correo
     * @param entity es el usuario que se va a buscar
     * @return devuelve false si no existe, o true si existe
     */
    private boolean entityExist(UserEntity entity) {
        if (userDao.findByEmail(entity.getEmail()) == null) {
            return false;
        }
        return true;
    }

    /**
     * En este metodo se comprueba el role de un usuario
     * @param user es el usuario al que se le va a comprobar el role
     * @return devuelve un 1 si el role es estudiante, devuelve un 2 si el role es profesor, devuelve un 3 si
     * el role es administrador
     */
    private int checkRoleUser(UserEntity user) {
        int idRole = 0;
        switch (user.getRole().getId()) {
            case 1:
                idRole = 1;
                break;
            case 2:
                idRole = 2;
                break;
            case 3:
                idRole = 3;
                break;
        }
        return idRole;

    }

    /**
     * En este metodo se añadira un nuevo usuario en la base de datos
     * @param entity es el usuario que se va a añadir
     * @param idUser es el id del usuario que va a añadir el nuevo usuario
     * @return devuelve el usuario que se ha añadido a la base de datos
     * @throws IncorrectUserException se lanzara esta excepcion si el usuario que intenta añadir al nuevo usuario no
     * es un administrador
     * @throws ClassListException se lanzara esta excepcion si el usuario es administrador y se le asigna alguna clase
     * @throws EntityExistsException esta excepcion se lanzara si el usuario que se intenta crear en la base de datos ya
     * existe, o lo que es lo mismo, si el correo que se le asigna ya se encuentra registrado
     * @throws IncorrectDataExpected esta excepcion se lanzara si a un administrador se le asigna alguna asignatura
     * @throws ClassListExceptionStudent se lanzara esta excepcion si a un estudiante se le asigna mas de una clase
     */
    public UserEntity addUser(UserEntity entity, Integer idUser) throws IncorrectUserException,
            ClassListException, EntityExistsException, IncorrectDataExpected, ClassListExceptionStudent {
        entity.setPass(Config.createPass());
        String email = "@email.app";
        if (!entityExist(entity)) {

            int idRole = checkRoleUser(entity);
            // if (!entity.getClassEntities().isEmpty()) {
            if (idRole == 1) {
                if (entity.getClassEntities().size() > 1) {
                    throw new ClassListExceptionStudent();
                }
            } else if (idRole == 3) {
                if (!entity.getClassEntities().isEmpty()) {
                    throw new ClassListException();
                }
                if (!entity.getSubjectList().isEmpty()){
                    throw new IncorrectDataExpected();
                }
            }
            // }
            UserEntity admin = userDao.findById(idUser).orElseThrow();
            if (userIsAdmin(admin)) {
                //entity.setUsuCre(admin.getId());
                //
                //String emailUser = "";
                //if (entity.getSecondSurname() != null) {
                //    emailUser = entity.getName() + entity.getFirstSurname() + entity.getSecondSurname();
                //
                //} else {
                //    emailUser = entity.getName() + entity.getFirstSurname();
                //}
                //
                //entity.setEmail(checkEmail(emailUser, email));
                return userDao.save(entity);
            } else throw new IncorrectUserException();
        } else throw new EntityExistsException();
    }

    /**
     * En este metodo se genera un email automatico que se le pasara desde el metodo "addUser", este metodo se usara en
     * un futuro, y se le podra cambiar la terminacion del email a voluntad de la administracion (parte posterior al @)
     * @param emailUser es el email generado de la union del nombre y apellidos del usuario
     * @param email es la terminacion que se le añadiora al emailUser despues de generar uno que no exista en la base de
     *              dator
     * @return devuelve el email que tendra el nuevo usuario
     */
    private String checkEmail(String emailUser, String email) {

        if (userDao.findByEmail(emailUser + email) == null) {
            return (emailUser + email);

        } else {
            emailUser = emailUser + "1";

            if (userDao.findByEmail(emailUser + email) != null) {
                return checkEmail(emailUser, email);
            } else {
                return emailUser + email;
            }
        }
    }

    /**
     * En este metodo se actualiza la contraseña del usuario, primero se busca el usuario en la base de datos, y despues
     * se le actualiza la contrasñea
     * @param entity es el usuario con la contraseña nueva
     * @return devuelve al usuario con la contraseña ya actualizada
     */
    public UserEntity updateUser(UserEntity entity) {
        UserEntity user = getUser(entity.getId());
        user.setUsuMod(entity.getUsuMod());
        user.setDateMod(entity.getDateMod());
        user.setPass(entity.getPass());
        return userDao.save(user);
    }
}

