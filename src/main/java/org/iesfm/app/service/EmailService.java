package org.iesfm.app.service;

import org.iesfm.app.configuration.Config;
import org.iesfm.app.entity.SubjectEntity;
import org.iesfm.app.entity.UserEntity;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Properties;

@Service
public class EmailService {

    public void sendEmail(SubjectEntity subject, UserEntity student, UserEntity teacher, double newCountHours, BigDecimal newPercentage, int maxPercentage, Integer totalHours) throws AddressException, MessagingException, IOException {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.user", Config.EMAIL_SEND);
        props.setProperty("mail.smtp.ssl.protocls", "TLSv1.2");
        props.setProperty("mail.smtp.auth", "true");


        Session session = Session.getDefaultInstance(props);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(Config.EMAIL_SEND, false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(student.getEmail()));
        msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(teacher.getEmail()));


        msg.setSubject("Superado el limite de faltas " + LocalDate.now() + subject.getName());
        msg.setContent(
                "Buenos días " + student.getName() + " " + student.getFirstSurname() + ".<br>" +
                        "Ha superado el límite de faltas para la asignatura " + subject.getName() + "<br>" +
                        "Actualmente tiene un total de " + newCountHours + " faltas de asistencia, que suponen un " + newPercentage + "% de las horas totales: " + totalHours + " y el " +
                        "máximo permitido es: " + maxPercentage + "<br>" +
                        "Si existe algún error, póngase en contacto con la administración de su centro<br>" +
                        "Por favor no conteste a este correo, se genera automáticamente",
                "text/html");
        Transport transport = session.getTransport("smtp");
        transport.connect(Config.EMAIL_SEND, "hejevimvqdxzrgoc");
        transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
        transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.CC));
        transport.close();

    }
}
