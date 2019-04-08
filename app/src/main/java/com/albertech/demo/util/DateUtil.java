package com.albertech.demo.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateUtil {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss", Locale.CHINA);

    public static String format(long time) {
        return FORMAT.format(time);
    }

}
