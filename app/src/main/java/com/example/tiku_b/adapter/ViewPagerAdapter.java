package com.example.tiku_b.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.tiku_b.fragment.YinDaoYeFragment;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<YinDaoYeFragment> fragments;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior, List<YinDaoYeFragment> fragments) {
        super(fm, behavior);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
