package org.iesfm.app.service;

import org.iesfm.app.dao.AbsenceDao;
import org.iesfm.app.dao.SubjectDao;
import org.iesfm.app.entity.AbsenceEntity;
import org.iesfm.app.entity.SubjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbsenceService {

    @Autowired
    private AbsenceDao absenceDao;

    public List<AbsenceEntity> findAll() {
        return absenceDao.findAll();
    }

    public AbsenceEntity findByid(int id) {
        return absenceDao.findById(id).orElse(null);
    }

    public AbsenceEntity save(AbsenceEntity absenceEntity) {
        return absenceDao.save(absenceEntity);
    }

    public void deleteById(int id) {
        absenceDao.deleteById(id);
    }

}
