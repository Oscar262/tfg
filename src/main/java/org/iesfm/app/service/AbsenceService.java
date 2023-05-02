package org.iesfm.app.service;

import org.iesfm.app.dao.AbsenceDao;
import org.iesfm.app.entity.AbsenceEntity;
import org.iesfm.app.entity.SubjectEntity;
import org.iesfm.app.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbsenceService {

    @Autowired
    private AbsenceDao absenceDao;

/*
    public AbsenceDao getAbsenceDao() {
        return absenceDao;
    }


    public List<AbsenceEntity> findAll() {
        return absenceDao.findAll();
    }

    public AbsenceEntity findByid(int id) {
        return absenceDao.findById(id).orElse(null);
    }



    public void deleteById(int id) {
        absenceDao.deleteById(id);
    }

     */

    public AbsenceEntity addAbsence(AbsenceEntity absenceEntity, Integer idSubject, Integer idStudent, Integer idTeacher) {
/*
        if (absenceDao.findById(absenceEntity.getId()).isPresent()){
            throw new EntityExistsException();
        }
        esto es para los otros servicios, en las ausencias no tiene sentido hacer esto

 */
        UserService userService = new UserService();
        SubjectService subjectService = new SubjectService();

        UserEntity student = userService.getUser(idStudent);
        SubjectEntity subject = subjectService.getSubject(idSubject);
        UserEntity teacher = userService.getUser(idTeacher);




        absenceEntity.setStudent(userService.getUser(student.getId()));
        absenceEntity.setTeacherCre(userService.getUser(teacher.getId()));
        absenceEntity.setSubject(subjectService.getSubject(subject.getId()));

            return absenceDao.save(absenceEntity);
    }

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
