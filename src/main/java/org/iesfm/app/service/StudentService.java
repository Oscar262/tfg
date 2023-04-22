package org.iesfm.app.service;

import org.iesfm.app.dao.StudentDao;
import org.iesfm.app.entity.StudentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentDao studentDao;

    public boolean checkUsser(String email, String pass) {
        StudentEntity student = studentDao.findByEmailAndPass(email, pass);
        if (student != null) {
            return true;
        } else return false;
    }
}
