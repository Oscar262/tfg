package org.iesfm.app.configuration;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Config {

    public static int MAX_PERCENTAGE = 50;
    public static String EMAIL_SEND = "asistloapptfg@gmail.com";

    public static LocalDate END_DATE = configEndDate();

    public static LocalDate START_DATE = configStartDate();

    public static int MAX_HOURS = 7;

    public static String createPass() {
        SecureRandom random = new SecureRandom();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";


        return IntStream.range(0, 10)
                .map(i -> random.nextInt(chars.length()))
                .mapToObj(randomIndex -> String.valueOf(chars.charAt(randomIndex)))
                .collect(Collectors.joining());
    }


    private static LocalDate configStartDate() {
        if (LocalDate.now().isBefore(LocalDate.of(LocalDate.now().getYear(), 9, 1))) {
            return LocalDate.of(LocalDate.now().getYear() - 1, 9, 1);
        }
        return LocalDate.of(LocalDate.now().getYear(), 9, 1);
    }

    private static LocalDate configEndDate() {
        if (LocalDate.now().isAfter(LocalDate.of(LocalDate.now().getYear() + 1, 7, 1))) {
            return LocalDate.of(LocalDate.now().getYear() - 1, 7, 1);
        }
        return LocalDate.of(LocalDate.now().getYear(), 7, 1);
    }
}
