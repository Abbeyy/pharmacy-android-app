package com.nsa.welshpharmacy.model;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by c1712480 on 14/05/2018.
 */

public class Utils {

    /**
     * Sorts a map by descending values
     */
//Retrieved from: https://stackoverflow.com/a/2581754
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
