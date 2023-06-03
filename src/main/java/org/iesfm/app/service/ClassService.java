package org.iesfm.app.service;

import org.iesfm.app.dao.ClassDao;
import org.iesfm.app.entity.ClassEntity;
import org.iesfm.app.entity.UserEntity;
import org.iesfm.app.exceptions.IncorrectUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;

@Service
public class ClassService {

    @Autowired
    private ClassDao classDao;

    @Autowired
    private UserService userService;

    public List<ClassEntity> findAll() {
        return classDao.findAll();
    }

    public ClassEntity findByid(int id) {
        return classDao.findById(id).orElse(null);
    }

    public ClassEntity save(ClassEntity classEntity) {
        return classDao.save(classEntity);
    }

    public void deleteById(int id) {
        classDao.deleteById(id);
    }

    public List<ClassEntity> findAllClassesByTeacher(Integer idTeacher) {
        return classDao.findByUserEntities_Id(idTeacher);

    }

    private boolean entityExist(ClassEntity entity) {
        if (classDao.findByName(entity.getName()) != null) {
            throw new EntityExistsException();
        }
        return false;
    }


    public ClassEntity addClass(ClassEntity entity, Integer idUser) throws IncorrectUserException {
        UserEntity user = userService.getUser(idUser);
        if (userService.userIsAdmin(user)) {
            entity.setUserCre(user);
            if (!entityExist(entity)) {
                return classDao.save(entity);
            } else throw new EntityExistsException();
        }
        throw new IncorrectUserException();
    }


    public List<ClassEntity> findAllclasses(Integer idUser) {
        List<ClassEntity> classEntities = classDao.findAll();

        UserEntity user = null;
        if (userService.getUser(idUser) != null) {
            user = userService.getUser(idUser);
            List<ClassEntity> classUser = user.getClassEntities();

            for (ClassEntity entity : classEntities) {
                if (classUser.contains(entity)) {
                    classEntities.remove(entity);
                }
            }

        }
        return classEntities;
    }
}
