package com.example.news;

import android.app.Application;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar mToolbar;
    private List<Fragment> mFragmentList;
    private MyApplication app;
    private String tabTitleArray[];
    private int tabTitleIndex[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        mToolbar  = (Toolbar) findViewById(R.id.toolBar);

        setSupportActionBar(mToolbar);

        //添加页面
        mFragmentList = new ArrayList<>();
        int[] titleIndex = app.getTabTitleArrayIndex();
        tabTitleArray = new String[titleIndex.length];
        tabTitleIndex = new int[titleIndex.length];
        for(int i = 0; i < tabTitleArray.length; i++){
            addFragment(new TabFragment().putIndex(titleIndex[i]), i, titleIndex[i], switchNewsType(titleIndex[i]));
        }

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this, mFragmentList, tabTitleArray);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    /*
    * 添加碎片
    * 数组编号
    * 新闻类型编号
    * 新闻类型
    * */
    public void addFragment(Fragment fragment, int i, int index, String title) {
        mFragmentList.add(fragment);
        tabTitleArray[i] = title;
        tabTitleIndex[i] = index;
    }

    //判断新闻类型
    public String switchNewsType(int type){
        String s = "";
        switch (type){
            case 1 : s = getString(R.string.news_type1);break;
            case 2 : s = getString(R.string.news_type2);break;
            case 3 : s = getString(R.string.news_type3);break;
            case 4 : s = getString(R.string.news_type4);break;
            case 5 : s = getString(R.string.news_type5);break;
        }
        return s;
    }



}

