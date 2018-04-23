package com.nsa.welshpharmacy.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by c1502032 on 23/04/2018.
 */

public class Pager extends FragmentStatePagerAdapter {

    int tabCount;

    public Pager(FragmentManager fragmentManager, int tabCount) {
        super(fragmentManager);
        this.tabCount=tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                UserFilterPreferenceActivityVersion2ServicesFragment userFilterPreferenceActivityVersion2ServicesFragment = new UserFilterPreferenceActivityVersion2ServicesFragment();
                return userFilterPreferenceActivityVersion2ServicesFragment;
            case 1:
                UserFilterPreferenceActivityVersion2LocationFragment userFilterPreferenceActivityVersion2LocationFragment = new UserFilterPreferenceActivityVersion2LocationFragment();
                return userFilterPreferenceActivityVersion2LocationFragment;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
