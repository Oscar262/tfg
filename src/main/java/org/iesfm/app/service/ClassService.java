package org.iesfm.app.service;

import org.iesfm.app.dao.ClassDao;
import org.iesfm.app.entity.ClassEntity;
import org.iesfm.app.entity.UserEntity;
import org.iesfm.app.exceptions.IncorrectUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Set;

/**
 * Esta clase se encuentran los metodos relacionados con el servicio de la aplicacion que afectan a las clases
 */
@Service
public class ClassService {

    @Autowired
    private ClassDao classDao;

    @Autowired
    private UserService userService;

    /**
     * En este metodo se busca una lista con todas las clases que existen en la base de datos
     * @return devuelve la lista de las clases
     */
    public List<ClassEntity> findAll() {
        return classDao.findAll();
    }

    /**
     * En este metodo se busca una clase en la base de datos
     * @param id es el id de la clase que se va a buscar
     * @return devuevle la clase
     */
    public ClassEntity findByid(int id) {
        return classDao.findById(id).orElse(null);
    }

    /**
     * En este metodo se borra una clase
     * @param id el id de la clase a borrar
     */
    public void deleteById(int id) {
        classDao.deleteById(id);
    }

    /**
     * En este metodo se buscara una lista de clases en la base de datos
     * @param idTeacher es el id del profesor
     * @return devuelve la lista de clases de un profesor
     */
    public List<ClassEntity> findAllClassesByTeacher(Integer idTeacher) {
        return classDao.findByUserEntities_Id(idTeacher);

    }

    /**
     * En este metodo se comprueba si existe la clase en la base de datos
     * @param entity es la clase que se va a buscar en la base de datos
     * @return devuelve false si la clase no existe, o true si existe
     */
    private boolean entityExist(ClassEntity entity){
        if (classDao.findByName(entity.getName()) != null) {
            return true;
        }
        return false;
    }


    /**
     * En este metodo se guarda una nueva clase en la base de datos
     * @param entity es la clase que se va a guardar
     * @param idUser es el id del usuario que ha creado la nueva clase
     * @return devuelve la clase que se ha guardado en la base de datos
     * @throws IncorrectUserException se lanzara esta excepcion si el usuario que intenta guardar la clase no es admin
     * @throws EntityExistsException se lanzara esta excepcion si la clase que se intenta guardar ya existe
     */
    public ClassEntity addClass(ClassEntity entity, Integer idUser) throws IncorrectUserException, EntityExistsException{
        UserEntity user = userService.getUser(idUser);
        if (userService.userIsAdmin(user)) {
            entity.setUserCre(user);
            if (!entityExist(entity)) {
                return classDao.save(entity);
            } else throw new EntityExistsException();
        }
        throw new IncorrectUserException();
    }

    /**
     * En este metodo se buscan las clases que no estan relacionadas un usuario, este metodo se usara en un futuro
     * cuando se quiera actualizar un usuario para añadirle a la lista nuevas clases
     * @param idUser es el usuario con el que se comprobara la lista total de las clases
     * @return devuelve la lista de clases
     */
    public List<ClassEntity> findAllclasses(Integer idUser) {
        List<ClassEntity> classEntities = classDao.findAll();

        UserEntity user = null;
        if (userService.getUser(idUser) != null) {
            user = userService.getUser(idUser);
            Set<ClassEntity> classUser = user.getClassEntities();

            for (ClassEntity entity : classEntities) {
                if (classUser.contains(entity)) {
                    classEntities.remove(entity);
                }
            }

        }
        return classEntities;
    }
}
