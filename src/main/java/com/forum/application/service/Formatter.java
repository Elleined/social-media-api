package com.forum.application.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface Formatter {
    static String formatDate(LocalDate date) {
        String month = date.getMonth().name();
        String finalMonth = month
                .substring(0, 1)
                .toUpperCase() +
                month.substring(1).toLowerCase();

        String day = String.valueOf(date.getDayOfMonth());
        String year = String.valueOf(date.getYear());
        return String.format("%s %s, %s", finalMonth, day, year);
    }

    static String formatDate(LocalDateTime dateTime) {
        String month = dateTime.getMonth().name();
        String finalMonth = month
                .substring(0, 1)
                .toUpperCase() +
                month.substring(1).toLowerCase();

        String day = String.valueOf(dateTime.getDayOfMonth());
        String year = String.valueOf(dateTime.getYear());
        return String.format("%s %s, %s", finalMonth, day, year);
    }

    static String formatDateWithoutYear(LocalDateTime dateTime) {
        String month = dateTime.getMonth().name();
        String finalMonth = month
                .substring(0, 1)
                .toUpperCase() +
                month.substring(1).toLowerCase();

        String day = String.valueOf(dateTime.getDayOfMonth());
        return String.format("%s %s", finalMonth, day);
    }

    static String formatTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return formatter.format(dateTime);
    }

    static String formatString(String field) {
        return field.substring(0, 1).toUpperCase() +
                field.substring(1).toLowerCase();
    }
}
