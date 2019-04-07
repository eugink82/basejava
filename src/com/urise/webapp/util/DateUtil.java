package com.urise.webapp.util;

import java.time.*;

public class DateUtil {
    public static LocalDate of(int year, Month month){
        return LocalDate.of(year, month,1);
    }
}
