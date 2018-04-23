package com.nsa.welshpharmacy.stores;

import com.nsa.welshpharmacy.model.Language;
import com.nsa.welshpharmacy.services.FirebaseServices;

import java.util.List;

/**
 * Creates a list of languages as loaded in from the database
 *
 * Created by c1712480 on 30/03/2018.
 */

public class LanguagesStore {

    private List<Language> languages = null;

    public List<Language> getLanguages(){
        if(languages == null){
            languages = FirebaseServices.loadLanguages();
        }
        return languages;
    }
}
