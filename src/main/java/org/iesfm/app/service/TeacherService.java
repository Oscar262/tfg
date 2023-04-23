package org.iesfm.app.service;

import org.iesfm.app.dao.TeacherDao;
import org.iesfm.app.entity.TeacherEntity;
import org.iesfm.app.exceptions.NotAdminException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    @Autowired
    TeacherDao teacherDao;

    public Boolean checkUsser(String email, String pass) throws NotAdminException {
        TeacherEntity teacher = teacherDao.findByEmailAndPass(email, pass);
        if (teacher != null) {¡
            if (teacher.getAdmin() != null){¡
                return true;
            } else {
                throw new NotAdminException();
            }
        } else return false;
    }
}
