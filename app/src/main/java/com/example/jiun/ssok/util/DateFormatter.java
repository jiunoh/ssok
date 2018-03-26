package com.example.jiun.ssok.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    public static String getFormatted(Date date) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
}
