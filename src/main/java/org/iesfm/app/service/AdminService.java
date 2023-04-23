package org.iesfm.app.service;

import org.iesfm.app.dao.AdminDao;
import org.iesfm.app.entity.AdminEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    AdminDao adminDao;

    public boolean checkUsser(String email, String pass) {
        AdminEntity admin = adminDao.findByEmailAndPass(email, pass);
        if (admin != null) {
            return true;
        } else return false;
    }
}
