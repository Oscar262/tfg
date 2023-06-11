package org.iesfm.app.configuration;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * En esta clase se crean algunas variables que se utilizarn en el codigo
 */
public class Config {

    /**
     * Esta variable representa el porcentaje maximo de faltas que un alumno puede tener por asignatura
     */
    public static int MAX_PERCENTAGE = 50;

    /**
     * Esta variable representa el correo electronico de la aplicacion desde el que se enviará el correo a los alumnos
     * y profesores cuando un alumno haya superdao el porcentaje maximo de faltas
     */
    public static String EMAIL_SEND = "asistloapptfg@gmail.com";

    /**
     * Esta variable represtena el final del curso escolar
     */
    public static LocalDate END_DATE = configEndDate();

    /**
     * Esta variable represtena el inicio del curso escolar
     */
    public static LocalDate START_DATE = configStartDate();

    /**
     * Esta variable representa el maximo de horas que un alumno puede estar al dia en el instituto
     */
    public static int MAX_HOURS = 7;

    /**
     * En este metodo se genera un contraseña automatica con 10 digitos
     * @return Devuelve la contraseña generada
     */
    public static String createPass() {
        SecureRandom random = new SecureRandom();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";


        return IntStream.range(0, 10)
                .map(i -> random.nextInt(chars.length()))
                .mapToObj(randomIndex -> String.valueOf(chars.charAt(randomIndex)))
                .collect(Collectors.joining());
    }

    /**
     * En este metodo se calcula el inicio del año escolar, en caso de que la falta se genere en octubre se utiliza el
     * año actual, en caso de que se genere la falta en mayo, se utiliza el año anterior, porque los cursos escolares van
     * de septiembre a junio
     * @return Devuelve la fecha de inicio del curso escolar
     */
    private static LocalDate configStartDate() {
        if (LocalDate.now().isBefore(LocalDate.of(LocalDate.now().getYear(), 9, 1))) {
            return LocalDate.of(LocalDate.now().getYear() - 1, 9, 1);
        }
        return LocalDate.of(LocalDate.now().getYear(), 9, 1);
    }

    /**
     * En este metodo se calcula el final del año escolar, en caso de que la falta se genere en octubre se utiliza el
     * año siguiente, en caso de que se genere la falta en mayo, se utiliza el año actual, porque los cursos escolares van
     * de septiembre a junio
     * @return Devuelve la fecha de fin del curso escolar
     */
    private static LocalDate configEndDate() {
        if (LocalDate.now().isAfter(LocalDate.of(LocalDate.now().getYear() + 1, 7, 1))) {
            return LocalDate.of(LocalDate.now().getYear() - 1, 7, 1);
        }
        return LocalDate.of(LocalDate.now().getYear(), 7, 1);
    }
}
