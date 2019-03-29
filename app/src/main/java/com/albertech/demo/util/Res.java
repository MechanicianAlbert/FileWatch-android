package com.albertech.demo.util;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;


public class Res {

    private static Application sApplication;


    private Res() {
        throw new RuntimeException("This class cannot be instantiated");
    }


    static void setApplication(Application a) {
        sApplication = a;
    }

    public static Application application() {
        return sApplication;
    }

    public static Context context() {
        return sApplication.getApplicationContext();
    }

    public static String string(@StringRes int stringRes) {
        return context().getResources().getString(stringRes);
    }

    public static int color(@ColorRes int colorRes) {
        return context().getResources().getColor(colorRes);
    }

    public static Drawable drawable(@DrawableRes int drawableRes) {
        return context().getResources().getDrawable(drawableRes);
    }

    public static int px(@DimenRes int dimenRes) {
        return context().getResources().getDimensionPixelSize(dimenRes);
    }

}
