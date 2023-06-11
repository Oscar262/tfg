package org.iesfm.app.service;

import org.iesfm.app.dao.SubjectDao;
import org.iesfm.app.entity.SubjectEntity;
import org.iesfm.app.entity.UserEntity;
import org.iesfm.app.exceptions.IncorrectUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Set;

/**
 * Esta clase se encuentran los metodos relacionados con el servicio de la aplicacion que afectan a las asignaturas
 */
@Service
public class SubjectService {

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private UserService userService;

    /**
     * En este metodo se buscan todas las asignaturas que se encuentran en la base de datos
     * @return devuelve la lista con las asignaturas
     */
    public List<SubjectEntity> findAll() {
        return subjectDao.findAll();
    }

    /**
     * En este metodo se busca una asignatura en la base de datos
     * @param id es el id de la asignatura que se va a buscar
     * @return devuelve la asignatura
     */
    public SubjectEntity getSubject(int id) {
        return subjectDao.findById(id).orElse(null);
    }

    /**
     * En este metodo se elimina una asignatura
     * @param id el id de la asignatura a eliminar
     */
    public void deleteById(int id) {
        subjectDao.deleteById(id);
    }

    /**
     * En este metodo se comprueba si ya existe una asignatura en la base de datos
     * @param entity es la asignatura que se va a buscar
     * @return devuelve false si esta no existe o true si existe
     */
    private boolean entityExist(SubjectEntity entity) {
        if (subjectDao.findByName(entity.getName()) != null) {
            return true;
        }
        return false;
    }

    /**
     * En este metodo se genera una nueva asignatura en la base de datos
     * @param entity es la asignatura que se quiere guardar en la base de datos
     * @param idUser es el id del usuario que crea la nueva asignatura en la base de datos
     * @return devuelve la asignatura que se ha guardado en la base de datos
     * @throws IncorrectUserException esta excepcion se lanzara cuando el usuario que intenta crear la asignatura no sea
     * un admin
     * @throws EntityExistsException esta excepcion se lanzara si ya existe la asignatura
     */
    public SubjectEntity addSubject(SubjectEntity entity, Integer idUser) throws IncorrectUserException, EntityExistsException {
        UserEntity user = userService.getUser(idUser);
        if (userService.userIsAdmin(user)) {
            entity.setUserCre(user);
            if (!entityExist(entity)) {
                return subjectDao.save(entity);
            } else throw new EntityExistsException();
        }
        throw new IncorrectUserException();
    }

    /**
     * En este metodo se buscan las asignaturas que no esta relacionada un usuario, este metodo se usara en un futuro
     * cuando se quiera actualizar un usuario para añadirle a la lista nuevas asignaturas
     * @param idUser es el usuario con el que se comprobara la lista total de las asignaturas
     * @return devuelve la lista de las asignaturas
     */
    public List<SubjectEntity> findAllSubjects(Integer idUser) {
        List<SubjectEntity> subjectEntities = subjectDao.findAll();

        UserEntity user = null;
        if (userService.getUser(idUser) != null) {
            user = userService.getUser(idUser);
            Set<SubjectEntity> subjectsUser = user.getSubjectList();
            subjectEntities.removeIf(subjectsUser::contains);
        }

        return subjectEntities;
    }
}
