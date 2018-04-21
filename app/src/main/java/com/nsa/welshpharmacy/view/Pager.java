package com.nsa.welshpharmacy.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by c1502032 on 20/04/2018.
 */

public class Pager extends FragmentStatePagerAdapter {

    int tabCount;

    public Pager(FragmentManager fm, int tabCount ) {
        super(fm);
        this.tabCount = tabCount;
    }



    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                UserFilterPreferenceActivityServicesFragment userFilterPreferenceActivityServicesFragment = new UserFilterPreferenceActivityServicesFragment();
                return userFilterPreferenceActivityServicesFragment;
            case 1:
                UserFilterPreferenceActivityLocationFragment userFilterPreferenceActivityLocationFragment = new UserFilterPreferenceActivityLocationFragment();
                return userFilterPreferenceActivityLocationFragment;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
