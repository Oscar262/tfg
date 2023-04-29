package org.iesfm.app.service;

import org.iesfm.app.dao.UserDao;
import org.iesfm.app.entity.UserEntity;
import org.iesfm.app.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao dao;

    public UserEntity checkUser(String email, String pass) throws UserNotFoundException {

        UserEntity entity = dao.findByEmailAndPass(email,pass);
        if (entity == null){
            throw new UserNotFoundException();
        }
        return entity;

    }
}
