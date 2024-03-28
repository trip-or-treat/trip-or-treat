package com.triportreat.backend.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomDateUtil {

    public static String toStringFormat(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
