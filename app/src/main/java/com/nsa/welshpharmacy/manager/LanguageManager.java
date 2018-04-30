package com.nsa.welshpharmacy.manager;

import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

/**
 * This class is used to translate text inside of the
 * application between English and Welsh strings.
 * As of 30/04/18, it relies on the strings.xml file
 * and the values-cy strings.xml file for this to work.
 *
 * Created by c1714546 on 4/21/2018.
 *
 * @author Abbey Ross.
 * @version 1.0 April 30th, 2018.
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
                config.locale = Locale.ENGLISH;
                break;
        }

        // Update configuration to read in text from the
        // correct values-... resources.
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

}
