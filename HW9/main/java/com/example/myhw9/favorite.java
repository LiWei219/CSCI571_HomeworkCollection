package com.example.myhw9;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/4/16.
 */

public class favorite extends Fragment{
    private String TAG = MainActivity.class.getSimpleName();

    public TabLayout tabLayout;
    public ViewPager viewPager;
    public View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.favorite,container,false);
        viewPager = (ViewPager)view.findViewById(R.id.favviewpager);
        tabLayout = (TabLayout)view.findViewById(R.id.favtab);
        initViewPager();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Favorite");
    }

    public void initViewPager(){
        List<String> titles = new ArrayList<>();
        titles.add("user");
        titles.add("page");
        titles.add("event");
        titles.add("place");
        titles.add("group");
        List<Fragment> fragments = new ArrayList<>();

        fragments.add(new Favuser());
        fragments.add(new Favpage());
        fragments.add(new Favevent());
        fragments.add(new Favplace());
        fragments.add(new Favgroup());

        FragmentAdapter mFragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(),fragments,titles);
        //给ViewPager设置适配器
        viewPager.setAdapter(mFragmentAdapter);
        //将TabLayout和ViewPager关联起来。
        tabLayout.setupWithViewPager(viewPager);
        //给TabLayout设置适配器
        tabLayout.setTabsFromPagerAdapter(mFragmentAdapter);

        TextView tabzero = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.tab,null);
        tabzero.setText("    User");
        tabzero.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.users,0,0);
        tabLayout.getTabAt(0).setCustomView(tabzero);

        TextView tabone = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.tab,null);
        tabone.setText("    Page");
        tabone.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.pages,0,0);
        tabLayout.getTabAt(1).setCustomView(tabone);

        TextView tabtwo = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.tab,null);
        tabtwo.setText("    Event");
        tabtwo.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.events,0,0);
        tabLayout.getTabAt(2).setCustomView(tabtwo);

        TextView tabthree = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.tab,null);
        tabthree.setText("    Place");
        tabthree.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.places,0,0);
        tabLayout.getTabAt(3).setCustomView(tabthree);

        TextView tabfour = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.tab,null);
        tabfour.setText("    Group");
        tabfour.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.groups,0,0);
        tabLayout.getTabAt(4).setCustomView(tabfour);

    }
}
