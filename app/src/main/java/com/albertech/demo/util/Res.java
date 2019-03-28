package com.albertech.demo.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

public class Res {

    public static String string(@NonNull Context context, @StringRes int stringRes) {
        return context.getResources().getString(stringRes);
    }


}
