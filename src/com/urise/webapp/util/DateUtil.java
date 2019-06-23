package com.urise.webapp.util;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String format(LocalDate date) {
        if (date == null) return "";
        return date.equals(NOW) ? "Сейчас" : date.format(formatter);
    }

    public static LocalDate parseToLocalDate(String date) {
        if (date == null || date.trim().length() == 0 || "Сейчас".equals(date)) return NOW;
        return LocalDate.parse(date, formatter);
    }
}
