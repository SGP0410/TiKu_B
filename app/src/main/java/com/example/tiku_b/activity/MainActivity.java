package com.example.tiku_b.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.tiku_b.AppClient;
import com.example.tiku_b.R;
import com.example.tiku_b.adapter.ViewPagerAdapter;
import com.example.tiku_b.fragment.YinDaoYeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int[] tus = new int[]{R.mipmap.yingdao1 , R.mipmap.yingdao2 , R.mipmap.yingdao3 , R.mipmap.yingdao4 , R.mipmap.yingdao5};
    private List<YinDaoYeFragment> fragments;
    private ViewPager viewPager;
    private LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if (AppClient.getSharedPreferences().getBoolean("YD" , false)){
            startActivity(new Intent(this , HomeActivity.class));
        }else {
            showViewPager();
        }
    }
    //为ViewPager添加fragment，并显示
    private void showViewPager() {
        for (int value : tus) {
            if (value == tus[tus.length - 1]){
                fragments.add(new YinDaoYeFragment(value, "end"));
            }else {
                fragments.add(new YinDaoYeFragment(value, ""));
            }
        }
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager() , FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT , fragments));
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                showLayout(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setLayout();
    }
    //根据显示页面改变点的颜色
    private void showLayout(int position) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            if (i == position){
                ((ImageView) layout.getChildAt(i)).setImageResource(R.drawable.yd_hui);
            }else {
                ((ImageView) layout.getChildAt(i)).setImageResource(R.drawable.yd_hei);
            }
        }
    }
    //为layout添加子布局
    private void setLayout() {
        for (int i = 0; i < fragments.size(); i++) {
            ImageView imageView = new ImageView(this);
            if (i == 0){
                imageView.setImageResource(R.drawable.yd_hui);
            }else {
                imageView.setImageResource(R.drawable.yd_hei);
            }
            imageView.setLayoutParams(new LinearLayout.LayoutParams(110 , 30));
            imageView.setPadding(40 , 0 , 40 , 0);
            layout.addView(imageView);
        }
    }

    private void initView() {
        viewPager = findViewById(R.id.view_pager);
        layout = findViewById(R.id.layout);
        fragments = new ArrayList<>();
    }
}