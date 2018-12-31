package com.example.yuecanting.happyrestaurant;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 17631 on 2018/12/25.
 * viewpager的adapter（适配器）
 */

public class MainViewpagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    public MainViewpagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    /**
     * Return the Fragment associated with a specified position.
     */
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return fragmentList.size();
    }

}