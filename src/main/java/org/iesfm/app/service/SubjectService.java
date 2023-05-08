package org.iesfm.app.service;

import org.iesfm.app.dao.SubjectDao;
import org.iesfm.app.dao.UserDao;
import org.iesfm.app.entity.ClassEntity;
import org.iesfm.app.entity.SubjectEntity;
import org.iesfm.app.entity.UserEntity;
import org.iesfm.app.exceptions.IncorrectUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private UserService userService;

    public List<SubjectEntity> findAll() {
        return subjectDao.findAll();
    }

    public SubjectEntity getSubject(int id) {
        return subjectDao.findById(id).orElse(null);
    }

    public SubjectEntity save(SubjectEntity subjectEntity) {
        return subjectDao.save(subjectEntity);
    }

    public void deleteById(int id) {
        subjectDao.deleteById(id);
    }

    private boolean entityExist(SubjectEntity entity) {
        if (subjectDao.findByName(entity.getName()) != null) {
            throw new EntityExistsException();
        }
        return false;
    }


    public SubjectEntity addSubject(SubjectEntity entity, Integer idUser) throws IncorrectUserException {
        UserEntity user = userService.getUser(idUser);
        if (userService.userIsAdmin(user)) {
            entity.setUserCre(user);
            if (!entityExist(entity)) {
                return subjectDao.save(entity);
            } else throw new EntityExistsException();
        }
        throw new IncorrectUserException();
    }
}
