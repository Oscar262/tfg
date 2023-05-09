package org.iesfm.app.service;

import org.iesfm.app.configuration.Config;
import org.iesfm.app.dao.AbsenceDao;
import org.iesfm.app.entity.AbsenceEntity;
import org.iesfm.app.entity.SubjectEntity;
import org.iesfm.app.entity.UserEntity;
import org.iesfm.app.exceptions.IncorrectDataExpected;
import org.iesfm.app.exceptions.IncorrectDateException;
import org.iesfm.app.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class AbsenceService {

    @Autowired
    private AbsenceDao absenceDao;
    @Autowired
    private UserService userService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private EmailService emailService;


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

    public AbsenceEntity addAbsence(AbsenceEntity absenceEntity, Integer idSubject, Integer idStudent, Integer idTeacher) throws IncorrectDateException, IncorrectDataExpected, UserNotFoundException {
        int countAbsences = 0;
        int numHours = 0;

        LocalDate date = absenceEntity.getDate();

        UserEntity student = userService.getUser(idStudent);
        SubjectEntity subject = subjectService.getSubject(idSubject);
        UserEntity teacher = userService.getUser(idTeacher);

        if (teacher == null || student == null) {
            throw new UserNotFoundException();
        }

        List<AbsenceEntity> absences = absenceDao.findByDateAndStudent_Id(date, student.getId());

        for (AbsenceEntity absenceEntity1 : absences) {
            numHours += absenceEntity1.getNumHours();
        }


        if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            throw new IncorrectDateException();
        } else if (date.isAfter(Config.endDate) || date.isBefore(Config.startDate)) {
            throw new IncorrectDateException();

            //se necesita acceder a la base de datos y comprobar si las horas existentes mas las nuevas que se quieren agregar exceden las siete horas
            // ya que de ser así no se deberia de poder introducir la falta en la base de datos porque excederia las 7 horas diarias


        }
        if ((numHours + absenceEntity.getNumHours()) > Config.maxHours) {
            throw new IncorrectDataExpected();
        }

        double percentage = checkPercentage(subject, student, countAbsences);

        if (percentage < Config.maxPercentage) {
            double newCountHours = checkPercentage(subject, student, countAbsences) + absenceEntity.getNumHours();
            BigDecimal newPercentage = new BigDecimal(newCountHours).multiply(new BigDecimal(100)).divide(new BigDecimal(subject.getTotalHours()), 2, RoundingMode.HALF_UP);
            if (newCountHours >= Config.maxPercentage) {
                try {
                    emailService.sendEmail(subject, student, teacher, newCountHours, newPercentage, Config.maxPercentage, subject.getTotalHours());
                } catch (MessagingException | IOException e) {
                    e.printStackTrace();
                }
            }
        }


        absenceEntity.setStudent(userService.getUser(student.getId()));
        absenceEntity.setTeacherCre(userService.getUser(teacher.getId()));
        absenceEntity.setSubject(subjectService.getSubject(subject.getId()));

        return absenceDao.save(absenceEntity);
    }




    public List<AbsenceEntity> findAllAbsences(String studentName, String studentSurname, String subjectName) {

        List<AbsenceEntity> AbsenceList;

        if (subjectName != null && studentName != null && studentSurname != null) {
            AbsenceList = absenceDao.findBySubject_NameAndStudent_NameAndStudent_FirstSurname(subjectName, studentName, studentSurname);
        } else if (subjectName != null) {
            AbsenceList = absenceDao.findBySubject_Name(subjectName);
        } else if (studentName != null && studentSurname != null) {
            AbsenceList = absenceDao.findByStudent_NameAndStudent_FirstSurname(studentName, studentSurname);
        } else if (studentName != null) {
            AbsenceList = absenceDao.findByStudent_Name(studentName);
        } else if (studentSurname != null) {
            AbsenceList = absenceDao.findByStudent_FirstSurname(studentSurname);
        } else {
            AbsenceList = absenceDao.findAll();
        }

        return AbsenceList;
    }


    private double checkPercentage(SubjectEntity subject, UserEntity user, int countAbsences) {

        for (AbsenceEntity absence : user.getAbsenceList()) {
            if (absence.getSubject().getId().equals(subject.getId()) && absence.getStudent().equals(user)) {
                countAbsences += absence.getNumHours();
            }


        }
        return (double) (countAbsences * 100) / subject.getTotalHours();


    }
}
