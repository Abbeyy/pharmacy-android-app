package com.nsa.welshpharmacy;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by c1712480 on 18/04/2018.
 */

@RunWith(AndroidJUnit4.class)
public class PharmacyInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.nsa.welshpharmacy", appContext);
    }
}
