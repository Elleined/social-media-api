package com.elleined.socialmediaapi.utility;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public interface DateUtil {

    static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    static Date toDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }
}
