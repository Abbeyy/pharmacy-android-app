package com.nsa.welshpharmacy.view.language;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Locale;

/**
 * Created by c1714546 on 4/21/2018.
 */

public class LanguageManager {

    public static void changeLang(Resources res, String localeCode) {
        Configuration config = new Configuration(res.getConfiguration());

        switch (localeCode) {
            case "en" :
                config.locale = Locale.ENGLISH;
                break;
            case "cy" :
                config.locale = new Locale("cy");
                break;
            default :
                Log.i("DEV:", "issue with switching locale!");
                config.locale = Locale.ENGLISH;
                break;
        }

        res.updateConfiguration(config, res.getDisplayMetrics());
    }

}
