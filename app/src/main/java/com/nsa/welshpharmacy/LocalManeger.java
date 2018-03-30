package com.nsa.welshpharmacy;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;


/**
 * Created by c1660911 on 3/29/2018.
 */
class LocaleManager extends MainMenu {

    public static void setLocale(Context c) {
        setNewLocale(c, getLanguage(c));
    }

    private static String getLanguage(Context c) {
        return getLanguage(c);

    }


    public static void setNewLocale(Context c, String language) {
        persistLanguage(c, language);
        updateResources(c, language);
    }

    private static void persistLanguage(Context c, String language) {

    }

    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }
}