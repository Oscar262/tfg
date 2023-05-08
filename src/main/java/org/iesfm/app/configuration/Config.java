package org.iesfm.app.configuration;

import java.time.LocalDate;

public class Config {

    public static int maxPercentage = 50;
    public static String emailSend = "asistloapptfg@gmail.com";

    public static LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), 7, 1);

    public static LocalDate startDate = LocalDate.of(LocalDate.now().getYear() - 1, 9, 1);

}
