package com.example.myhw9;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.StrictMode;

/**
 * Created by Administrator on 2017/4/17.
 */

public class result extends AppCompatActivity{

    private String TAG = MainActivity.class.getSimpleName();

    public TabLayout tabLayout;
    public ViewPager viewPager;
    public static Context mcontext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        tabLayout = (TabLayout)findViewById(R.id.tablayout);

        mcontext = result.this;

        initViewPager();
    }

    public void initViewPager(){
        List<String> titles = new ArrayList<>();
        titles.add("user");
        titles.add("page");
        titles.add("event");
        titles.add("place");
        titles.add("group");
        List<Fragment> fragments = new ArrayList<>();

        fragments.add(new Fragmentuser());
        fragments.add(new Fragmentpage());
        fragments.add(new Fragmentevent());
        fragments.add(new Fragmentplace());
        fragments.add(new Fragmentgroup());

        FragmentAdapter mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),fragments,titles);
        //给ViewPager设置适配器
        viewPager.setAdapter(mFragmentAdapter);
        //将TabLayout和ViewPager关联起来。
        tabLayout.setupWithViewPager(viewPager);
        //给TabLayout设置适配器
        tabLayout.setTabsFromPagerAdapter(mFragmentAdapter);

        TextView tabzero = (TextView) LayoutInflater.from(this).inflate(R.layout.tab,null);
        tabzero.setText("    User");
        tabzero.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.users,0,0);
        tabLayout.getTabAt(0).setCustomView(tabzero);

        TextView tabone = (TextView) LayoutInflater.from(this).inflate(R.layout.tab,null);
        tabone.setText("    Page");
        tabone.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.pages,0,0);
        tabLayout.getTabAt(1).setCustomView(tabone);

        TextView tabtwo = (TextView) LayoutInflater.from(this).inflate(R.layout.tab,null);
        tabtwo.setText("    Event");
        tabtwo.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.events,0,0);
        tabLayout.getTabAt(2).setCustomView(tabtwo);

        TextView tabthree = (TextView) LayoutInflater.from(this).inflate(R.layout.tab,null);
        tabthree.setText("    Place");
        tabthree.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.places,0,0);
        tabLayout.getTabAt(3).setCustomView(tabthree);

        TextView tabfour = (TextView) LayoutInflater.from(this).inflate(R.layout.tab,null);
        tabfour.setText("    Group");
        tabfour.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.groups,0,0);
        tabLayout.getTabAt(4).setCustomView(tabfour);

    }

}
