package com.example.news;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.List;

/**
 * Created by chen on 16-8-22.
 */
public class    ViewPagerAdapter extends FragmentStatePagerAdapter {

    private String[] tabTitleArray;
    private List<Fragment> mFragments;

    public ViewPagerAdapter(FragmentManager fm, Context context, List<Fragment>fragmentList, String[]tabTitleArray){
        super(fm);
        this.tabTitleArray = tabTitleArray;
        mFragments = fragmentList;
    }


    @Override
    public Fragment getItem(int position) {

        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    /* 重写与TabLayout配合 */
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitleArray[position % tabTitleArray.length];
    }


}
