package org.iesfm.app.service;

import org.iesfm.app.dao.ClassDao;
import org.iesfm.app.entity.AbsenceEntity;
import org.iesfm.app.entity.ClassEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ClassService {

    @Autowired
    private ClassDao classDao;

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
}
