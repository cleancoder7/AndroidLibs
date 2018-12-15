package me.imstudio.core.utils;

import android.util.Log;

import java.util.Calendar;

public final class LogUtils {

    public static String TAG = LogUtils.class.getSimpleName();

    static boolean mIsDebug = true;

    public static void setTAG(String tag) {
        TAG = tag;
    }

    public static void setDebug(boolean isDebug) {
        mIsDebug = isDebug;
    }

    public static void log(String tag, String msg) {
        if (mIsDebug)
            Log.e(tag, String.format("%s_%s", Calendar.getInstance().getTimeInMillis(), msg));
    }

    public static void log(String msg) {
        log(TAG, msg);
    }
}
