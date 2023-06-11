package org.iesfm.app.service;

import org.iesfm.app.configuration.Config;
import org.iesfm.app.dao.AbsenceDao;
import org.iesfm.app.entity.AbsenceEntity;
import org.iesfm.app.entity.SubjectEntity;
import org.iesfm.app.entity.UserEntity;
import org.iesfm.app.exceptions.IncorrectDataExpected;
import org.iesfm.app.exceptions.IncorrectDateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;


/**
 * Esta clase se encuentran los metodos relacionados con el servicio de la aplicacion que afectan a las ausencias
 */
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

    /**
     * Este metodo busca toda la lista de ausencias en la base de datos
     * @return devuelve la lista
     */
    public List<AbsenceEntity> findAll() {
        return absenceDao.findAll();
    }

    /**
     * En este metodo se busca una ausencia en la base de datos
     * @param id el id de la ausencia a buscar
     * @return devuelve la ausencia que se ha buscado
     */
    public AbsenceEntity findByid(int id) {
        return absenceDao.findById(id).orElse(null);
    }


    /**
     * En este metodo se crea una nueva ausencia en la base de datos
     * @param absenceEntity es la ausencia que se debe guardar en la base de datos
     * @param idSubject es la asignatura a la que se ha faltado
     * @param idStudent es el estudiante que ha faltado
     * @param idTeacher es el profesor que ha mandado la falta
     * @return devuelve la ausencia que se ha guardado en la base de datos
     * @throws IncorrectDateException esta excepcion se lanzara si la fecha de la ausencia no es correcta
     * @throws IncorrectDataExpected esta excepcion se lanzara si al añadir esta ausencia se sobrepasa el nuemero maximo
     * de ausencias que un alumno puede tener en el mismo dia
     * @throws EntityNotFoundException esta excepcion se lanzara si el estudiante o el profesor no se encuentran en la
     * base de datos
     */
    public AbsenceEntity addAbsence(AbsenceEntity absenceEntity, Integer idSubject, Integer idStudent, Integer idTeacher)
            throws IncorrectDateException, IncorrectDataExpected, EntityNotFoundException {
        int countAbsences = 0;
        int numHours = 0;

        LocalDate date = absenceEntity.getDate();

        if (userService.getUser(idTeacher) == null || userService.getUser(idStudent) == null) {
            throw new EntityNotFoundException();
        }

        UserEntity student = userService.getUser(idStudent);
        SubjectEntity subject = subjectService.getSubject(idSubject);
        UserEntity teacher = userService.getUser(idTeacher);

        List<AbsenceEntity> absences = absenceDao.findByDateAndStudent_Id(date, student.getId());

        for (AbsenceEntity absenceEntity1 : absences) {
            numHours += absenceEntity1.getNumHours();
        }

        System.out.println(date.getDayOfWeek());
        if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            throw new IncorrectDateException();
        } else if (date.isAfter(Config.END_DATE) || date.isBefore(Config.START_DATE)) {
            throw new IncorrectDateException();


        }
        if ((numHours + absenceEntity.getNumHours()) > Config.MAX_HOURS) {
            throw new IncorrectDataExpected();
        }

        for (AbsenceEntity absence : student.getAbsenceList()) {
            if (absence.getSubject().getId().equals(subject.getId()) && absence.getStudent().equals(student)) {
                countAbsences += absence.getNumHours();
            }

        }

        double percentage = (double) (countAbsences * 100) / subject.getTotalHours();

        if (percentage < Config.MAX_PERCENTAGE) {
            double newCountHours = countAbsences + absenceEntity.getNumHours();
            BigDecimal newPercentage = new BigDecimal(newCountHours).multiply(new BigDecimal(100)).divide(new BigDecimal(subject.getTotalHours()), 2, RoundingMode.HALF_UP);
            if (newPercentage.compareTo(new BigDecimal(Config.MAX_PERCENTAGE)) >= 0) {
                try {
                    emailService.sendEmail(subject, student, teacher, newCountHours, newPercentage, Config.MAX_PERCENTAGE, subject.getTotalHours());
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

    /**
     * En este metodo se busca una lista de ausencias en la base de datos
     * @param teacherId es el profesor que ha creado las ausencias
     * @param subjectId es la asignatura para la que estan creadas las ausencias
     * @return devuelve una lista de ausencias que cumplan los dos campos anteriores
     */
    public List<AbsenceEntity> findAllAbsences(Integer teacherId, Integer subjectId) {

        return absenceDao.findByTeacherCre_IdAndSubject_IdOrderByStudent_IdAscDateDesc(teacherId, subjectId);
    }

    /**
     * En este metodo se elimina una ausencia en la base de datos
     * @param absenceId es la ausencia que se va a eliminar
     */
    public void delete(Integer absenceId) {
        absenceDao.deleteById(absenceId);
    }
}
