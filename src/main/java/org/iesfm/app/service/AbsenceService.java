package org.iesfm.app.service;

import org.iesfm.app.dao.AbsenceDao;
import org.iesfm.app.entity.AbsenceEntity;
import org.iesfm.app.entity.SubjectEntity;
import org.iesfm.app.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Service
public class AbsenceService {

    @Autowired
    private AbsenceDao absenceDao;
    @Autowired
    private UserService userService;
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private MailSender mailSender;

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
        int maxPercentage = 3;
        double countAbsences = 0.00;
/*
        if (absenceDao.findById(absenceEntity.getId()).isPresent()){
            throw new EntityExistsException();
        }
        esto es para los otros servicios, en las ausencias no tiene sentido hacer esto

 */
        UserEntity student = userService.getUser(idStudent);
        SubjectEntity subject = subjectService.getSubject(idSubject);
        UserEntity teacher = userService.getUser(idTeacher);

        double percentage = checkPercentage(subject, student, countAbsences);

        if (percentage < maxPercentage) {
            double newCountHours = checkPercentage(subject, student, countAbsences) + absenceEntity.getNumHours();
            double newPercentage = (newCountHours * 100) / subject.getTotalHours();
            if (newCountHours >= maxPercentage) {
                //TODO: crear metodo sendEmail(esta en el correo, se manda a dos correos, teacher y student, y en el texto del mensaja se pone el numero de faltas de asistencia,
                // el porcentage y el maximo de porcentage posible con las horas totales de la asignatura)
                try {
                    sendEmail(subject, student.getEmail(),teacher.getEmail(),newCountHours, newPercentage, maxPercentage, subject.getTotalHours());
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        absenceEntity.setStudent(userService.getUser(student.getId()));
        absenceEntity.setTeacherCre(userService.getUser(teacher.getId()));
        absenceEntity.setSubject(subjectService.getSubject(subject.getId()));

        return absenceDao.save(absenceEntity);
    }

    private void sendEmail(SubjectEntity subject, String studentEmail, String teacherEmail, double newCountHours, double newPercentage, int maxPercentage, Integer totalHours) throws AddressException, MessagingException, IOException {


        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");


        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("os.sanav.26@gmail.com", "Kirito262");
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("os.sanav.26@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(studentEmail));
        msg.setSubject("Superado el limite de faltas");
        msg.setContent(
                "Buenos días, ha superado el límite de faltas para la asignatura " + subject.getName() + ".\n" +
                "Actualmente tiene un total de " + newCountHours + " faltas de asistencia, que suponen un " + newPercentage+ "% de las horas totales: " + totalHours + " y el " +
                "máximo permitido es: " + maxPercentage+ ".\n" +
                "Si existe algún error, póngase en contacto con la administración de su centro",
                "text/html");

        Transport.send(msg);


/*
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(studentEmail, teacherEmail);
        message.setSubject("Superado el limite de faltas");
        message.setText("Buenos días, ha superado el límite de faltas para la asignatura " + subject.getName() + ".\n" +
                "Actualmente tiene un total de " + newCountHours + " faltas de asistencia, que suponen un " + newPercentage+ "% de las horas totales: " + totalHours + " y el " +
                "máximo permitido es: " + maxPercentage+ ".\n" +
                "Si existe algún error, póngase en contacto con la administración de su centro");
        mailSender.send(message);

 */

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


    private double checkPercentage(SubjectEntity subject, UserEntity user, Double countAbsences) {

        for (AbsenceEntity absence : user.getAbsenceList()) {
            if (absence.getSubject().getId().equals(subject.getId()) && absence.getStudent().equals(user)) {
                countAbsences += absence.getNumHours();
            }


        }
        return (countAbsences * 100) / subject.getTotalHours();


    }
}
