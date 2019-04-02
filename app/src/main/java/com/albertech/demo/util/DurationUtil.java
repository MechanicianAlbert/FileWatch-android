package com.albertech.demo.util;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class DurationUtil {

    private static final SimpleDateFormat FORMAT_WITH_HOUR = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
    private static final SimpleDateFormat FORMAT_WITHOUT_HOUR = new SimpleDateFormat("mm:ss", Locale.CHINA);

    public static String format(long duration) {
        if (duration > 1000 * 60 * 60) {
            return FORMAT_WITH_HOUR.format(duration);
        } else {
            return FORMAT_WITHOUT_HOUR.format(duration);
        }
    }
}
