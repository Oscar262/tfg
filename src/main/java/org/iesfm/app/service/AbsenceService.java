package org.iesfm.app.service;

import org.iesfm.app.dao.AbsenceDao;
import org.iesfm.app.dao.SubjectDao;
import org.iesfm.app.entity.AbsenceEntity;
import org.iesfm.app.entity.SubjectEntity;
import org.iesfm.app.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import java.util.Arrays;
import java.util.List;

@Service
public class AbsenceService {

    @Autowired
    private AbsenceDao absenceDao;

    public AbsenceDao getAbsenceDao() {
        return absenceDao;
    }

    /*
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


     */
    public List<AbsenceEntity> findAllAbsences(String studentName, String studentSurname, String subjectName) {

        List<AbsenceEntity> AbsenceList;

        if (subjectName != null && studentName != null && studentSurname != null){
            AbsenceList = absenceDao.findBySubject_NameAndStudent_NameAndStudent_FirstSurname(subjectName,studentName, studentSurname);
        }else if (subjectName != null){
            AbsenceList = absenceDao.findBySubject_Name(subjectName);
        }else if (studentName != null && studentSurname != null){
            AbsenceList = absenceDao.findByStudent_NameAndStudent_FirstSurname(studentName,studentSurname);
        }else if (studentName != null){
            AbsenceList = absenceDao.findByStudent_Name(studentName);
        }else if (studentSurname != null){
            AbsenceList = absenceDao.findByStudent_FirstSurname(studentSurname);
        }else {
            AbsenceList = absenceDao.findAll();
        }

        return AbsenceList;
    }
}
