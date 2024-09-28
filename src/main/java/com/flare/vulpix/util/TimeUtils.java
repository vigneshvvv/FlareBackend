package com.flare.vulpix.util;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    private TimeUtils(){}

    public static final String REQUIRED_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static  String getCurrentSystemTimeStamp(){
        return DateTimeFormatter.ofPattern(REQUIRED_DATE_TIME_FORMAT).format(LocalDateTime.now(Clock.systemUTC()));
    }
}
