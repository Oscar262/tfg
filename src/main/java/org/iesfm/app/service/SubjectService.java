package org.iesfm.app.service;

import org.iesfm.app.dao.SubjectDao;
import org.iesfm.app.entity.SubjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectDao subjectDao;

    public List<SubjectEntity> findAll() {
        return subjectDao.findAll();
    }

    public SubjectEntity findByid(int id) {
        return subjectDao.findById(id).orElse(null);
    }

    public SubjectEntity save(SubjectEntity subjectEntity) {
        return subjectDao.save(subjectEntity);
    }

    public void deleteById(int id) {
        subjectDao.deleteById(id);
    }
}
