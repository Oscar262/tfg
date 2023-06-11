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

/**
 * Esta clase se encuentran los metodos relacionados con el servicio de la aplicacion que afectan al envio por correo electronico
 */
@Service
public class EmailService {

    /**
     * En este metodo se genera un email que se enviara a un estudiante y a un profesor cuando el profesor le registre
     * una nueva falta al alumno y este supere el maximo de faltas posibles para una asignatura
     * @param subject es la asignatura a la que ha faltado el alumno
     * @param student es el alumno que ha faltado
     * @param teacher es el profesor que registra la falta
     * @param newCountHours es el numero de horas que ha faltado el alumno despues de haberle sumado las horas que
     *                      se han registrado en la nueva falta
     * @param newPercentage es el porcentaje de horas que ha faltado el alumno despues de haberle sumado las horas que
     *                      se han registrado en la nueva falta
     * @param maxPercentage es el porcentaje maximo que se puede faltar a una asignatura (se configura en la clase "Config")
     * @param totalHours es el numero de horas totales que tiene la asignatura
     * @throws AddressException es una excepcioon generica que se lanzara en algunos casos del metodo
     * @throws MessagingException es una excepcioon generica que se lanzara en algunos casos del metodo
     * @throws IOException es una excepcioon generica que se lanzara en algunos casos del metodo
     */
    public void sendEmail(SubjectEntity subject, UserEntity student, UserEntity teacher, double newCountHours, BigDecimal newPercentage, int maxPercentage, Integer totalHours)
            throws AddressException, MessagingException, IOException {

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
