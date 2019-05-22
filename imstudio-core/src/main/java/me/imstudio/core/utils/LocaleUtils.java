package me.imstudio.core.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import java.util.Locale;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;

public class LocaleUtils {

    private static final String TAG = LocaleUtils.class.getSimpleName();

    private static String DEFAULT_LANGUAGE_KEY;
    public static final String DEFAULT_LANGUAGE_VI = "vi";
    public static final String DEFAULT_LANGUAGE_EN = "en";
    private String DEFAULT_LANGUAGE;
    private static LocaleUtils localeUtils;

    private final SharedPreferences prefs;

    private LocaleUtils(@NonNull Context context) {
        DEFAULT_LANGUAGE_KEY = String.format("%s_%s", context.getPackageName(), TAG);
        this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.DEFAULT_LANGUAGE = DEFAULT_LANGUAGE_EN;
    }

    private LocaleUtils(@NonNull Context context, String firstLauncher) {
        this(context);
        this.DEFAULT_LANGUAGE = firstLauncher;
    }

    public static void install(Context context) {
        if (localeUtils == null)
            localeUtils = new LocaleUtils(context);
        localeUtils.setLocale(context);
    }

    public static void install(Context context, String firstLauncher) {
        if (localeUtils == null)
            localeUtils = new LocaleUtils(context, firstLauncher);
        localeUtils.setLocale(context);
    }

    public static LocaleUtils self() {
        // If null
        return localeUtils;
    }

    /*
     * get current language
     * */
    public String getLanguage() {
        return prefs.getString(DEFAULT_LANGUAGE_KEY, DEFAULT_LANGUAGE);
    }

    /*
     * get current language with default
     * */
    public String getLanguage(String language) {
        return prefs.getString(DEFAULT_LANGUAGE_KEY, language);
    }

    /*
     *  persist language in prefs
     * */
    private void persist(String language) {
        prefs.edit().putString(DEFAULT_LANGUAGE_KEY, language).apply();
    }

    public Context setLocale(Context context) {
        return updateResources(context, getLanguage());
    }

    public void setLocale(Context context, String language) {
        persist(language);
        updateResources(context, language);
    }

    /*
     *  Update resource
     * */
    private Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= JELLY_BEAN_MR1) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }

}
