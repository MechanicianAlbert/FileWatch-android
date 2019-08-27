package com.albertech.filehelper.log;

import android.text.TextUtils;
import android.util.Log;


public class LogUtil {

    private static final int STACK_TRACE_INDEX_OF_CALLER = 7;

    private static String sTag = "AAAAA";
    private static boolean sEnable = true;


    public static void setEnable(boolean enable) {
        sEnable = enable;
    }

    public static void setTag(String tag) {
        sTag = tag;
    }

    public static void d(String tag, Object... msg) {
        if (sEnable) {
            Log.d(sTag, getPrefix(tag) + flattenMsg(msg));
        }
    }

    public static void e(String tag, Object... msg) {
        if (sEnable) {
            Log.e(sTag, getPrefix(tag) + flattenMsg(msg));
        }
    }

    public static void e(String tag, Throwable tr) {
        if (sEnable) {
            Log.e(sTag, getPrefix(tag) + getThrowInfo(tr));
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (sEnable) {
            Log.e(sTag, getPrefix(tag) + msg + getThrowInfo(tr));
        }
    }


    private static String getPrefix(String tag) {
        tag = TextUtils.isEmpty(tag) ? "" : (tag + " --- ");
        return tag + getCallerInfo() + "\n";
    }

    private static String getCallerInfo() {
        return getClassName() + "." + getMethodName() + "(" + getFileNameName() + ":" + getLineNumber() + ")";
    }

    private static StackTraceElement getCaller() {
        return Thread.currentThread().getStackTrace()[STACK_TRACE_INDEX_OF_CALLER];
    }

    private static String getClassName() {
        String className = getCaller().getClassName();
        String shortClassName;
        if (className != null) {
            shortClassName = className.substring(className.lastIndexOf(".") + 1);
        } else {
            shortClassName = "";
        }
        return shortClassName;
    }

    private static String getMethodName() {
        return getCaller().getMethodName();
    }

    private static String getFileNameName() {
        return getCaller().getFileName();
    }

    private static String getLineNumber() {
        return String.valueOf(getCaller().getLineNumber());
    }

    private static String flattenMsg(Object... msgs) {
        StringBuilder b = new StringBuilder();
        if (msgs != null && msgs.length > 0) {
            for (Object msg : msgs) {
                if (msg != null) {
                    b.append(msg).append("\n");
                }
            }
        }
        return b.toString();
    }

    private static String getThrowInfo(Throwable t) {
        return "\n" + Log.getStackTraceString(t);
    }

}
