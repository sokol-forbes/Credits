package by.bsuir.app.services;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static by.bsuir.app.util.constants.Constants.NEW_FORMAT;
import static by.bsuir.app.util.constants.Constants.OLD_FORMAT;

public class DateHandler {

    public static String convertDateForDB(String date) throws DateTimeException {

        if (date.length() < 10)
            throw new DateTimeException("Неверная дата. (dd/MM/yyyy)");

        int day = Integer.parseInt(date.substring(0, 2));
        int month = Integer.parseInt(date.substring(3, 5));
        int year = Integer.parseInt(date.substring(6, 10));

        if (year > 2021 || year < 2015)
            throw new DateTimeException("Некорректный год.");

        System.out.println(day + "." + month + "." + year);
        isDateValid(year, month, day);

        return year + "-" + month + "-" + day;
    }

    public static boolean isDateValid(int year, int month, int day) throws DateTimeException {

        try {
            LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            throw new DateTimeException("Некорректная дата.");
        }
        return true;
    }

    public static String getCurrentTimeStampString() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);

        return formattedDate;
    }

    public static Date convertDateFormatInSqlDate(String oldDateString) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT, Locale.ENGLISH);
        java.util.Date d = sdf.parse(oldDateString);

        sdf.applyPattern(NEW_FORMAT);
        String newDateString = sdf.format(d);

        java.sql.Date sqlDate = java.sql.Date.valueOf(newDateString);
        System.out.println(newDateString);

        return sqlDate;
    }
}
