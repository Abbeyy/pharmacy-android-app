package com.nsa.welshpharmacy;

import com.nsa.welshpharmacy.model.Utils;

import junit.framework.Assert;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Junit tests to test methods in the Utils class
 * Created by c1712480 on 14/05/2018.
 */

public class UtilsTest {

    @Test
    public void testSortByValue(){
        //Given
        Map<String, Float> map = new HashMap<>();
        map.put("Pharmacy 1", 25.3f);
        map.put("Pharmacy 2", 13.2f);
        map.put("Pharmacy 3", 8.3f);
        map.put("Pharmacy 4", 27.5f);

        //When
        Map<String, Float> testMap = Utils.sortByValue(map);

        Map<String, Float> expectedOrderMap = new HashMap<>();
        expectedOrderMap.put("Pharmacy 3", 8.3f);
        expectedOrderMap.put("Pharmacy 2", 13.2f);
        expectedOrderMap.put("Pharmacy 1", 25.3f);
        expectedOrderMap.put("Pharmacy 4", 27.5f);

        //Then
        System.out.println(testMap.keySet());
        Assert.assertNotNull(testMap);
        Assert.assertFalse(testMap.isEmpty());
        Assert.assertEquals(4,testMap.values().size());
        Assert.assertNotNull(testMap.get("Pharmacy 1"));
        Assert.assertTrue(testMap.get("Pharmacy 1").equals(25.3f));
        Assert.assertEquals(expectedOrderMap, testMap);
    }
}
