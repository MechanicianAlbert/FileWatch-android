package com.albertech.demo.util;

import java.util.Locale;


public class SizeUtil {

    private static final int SCALE = 1024;

    private static final long K = SCALE;
    private static final long M = SCALE * K;
    private static final long G = SCALE * M;

    private static final String FORMAT = "%.2f %s";


    public static String format(double size) {
        if (size >= G) {
            return String.format(Locale.CHINA, FORMAT, size / G, "GB");
        } else if (size >= M){
            return String.format(Locale.CHINA, FORMAT, size / M, "MB");
        } else if (size >= K){
            return String.format(Locale.CHINA, FORMAT, size / K, "KB");
        } else {
            return String.format(Locale.CHINA, FORMAT, size, "B");
        }
    }

}
