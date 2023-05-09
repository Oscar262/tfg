package org.iesfm.app.configuration;

import java.time.LocalDate;

public class Config {

    public static int maxPercentage = 50;
    public static String emailSend = "asistloapptfg@gmail.com";

    public static LocalDate endDate = configEndDate();

    public static LocalDate startDate = configStartDate();

    public static int maxHours = 7;


    private static LocalDate configStartDate(){
        if (LocalDate.now().isBefore(LocalDate.of(LocalDate.now().getYear(), 9, 1))){
            return LocalDate.of(LocalDate.now().getYear() - 1, 9, 1);
        }
        return LocalDate.of(LocalDate.now().getYear(), 9, 1);
    }

    private static LocalDate configEndDate(){
        if (LocalDate.now().isAfter(LocalDate.of(LocalDate.now().getYear() + 1, 7, 1))){
            return LocalDate.of(LocalDate.now().getYear() - 1, 7, 1);
        }
        return LocalDate.of(LocalDate.now().getYear(), 7, 1);
    }
}
