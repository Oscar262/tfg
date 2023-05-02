package org.iesfm.app.service;

import org.iesfm.app.dao.UserDao;
import org.iesfm.app.dto.UserDto;
import org.iesfm.app.entity.UserEntity;
import org.iesfm.app.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public UserEntity checkUser(String email, String pass) throws UserNotFoundException {

        UserEntity entity = userDao.findByEmailAndPass(email, pass);
        if (entity == null) {
            throw new UserNotFoundException();
        }
        return entity;

    }

    public List<UserEntity> findAllStudents(String className, String subjectName, String role) {
        List<UserEntity> userEntities;

        if (subjectName != null && className != null){
            userEntities = userDao.findByRole_NameAndSubjectList_NameAndClassEntities_Name(role,subjectName,className);
        }else if (subjectName != null){
            userEntities = userDao.findByRole_NameAndSubjectList_Name(role, subjectName);
        }else if (className != null){
            userEntities = userDao.findByRole_NameAndClassEntities_Name(role,className);
        }else {
            userEntities = userDao.findByRole_Name(role);
        }

        return userEntities;
    }

    public UserEntity getStudent(Integer idUser) {
        return userDao.findById(idUser).orElseThrow();
    }
}
