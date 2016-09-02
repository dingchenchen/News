package com.example.news;

import android.app.Application;

/**
 * Created by chen on 16-8-22.
 */
public class MyApplication extends Application {


    //新闻类别编号,与string.xml对应
    public static int[]tabTitleArrayIndex = {1,2,3,4,5};
//    public static int[]tabTitleArrayIndex = {1,2,3,4,5};

    public static void setTabTitleArrayIndex(int[] tabTitleArrayIndex) {
        MyApplication.tabTitleArrayIndex = tabTitleArrayIndex;
    }

    public static int[] getTabTitleArrayIndex() {
        return tabTitleArrayIndex;
    }
}
