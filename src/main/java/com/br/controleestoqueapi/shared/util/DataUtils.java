package com.br.controleestoqueapi.shared.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DataUtils {
    public static final String DEFAULT_FORMAT = "dd/MM/yyyy";

    public static LocalDate stringToLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(DEFAULT_FORMAT));
    }

    public static boolean isDateValid(String value) {
        if(StringUtils.isNotEmpty(value)) {
            try {
                DataUtils.stringToLocalDate(value);
            } catch (DateTimeParseException e) {
                return false;
            }
        }

        return true;
    }

}
