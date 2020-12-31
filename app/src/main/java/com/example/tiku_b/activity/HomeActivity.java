package com.example.tiku_b.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.tiku_b.R;
import com.example.tiku_b.fragment.SYFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private BottomNavigationView nav;
    private FragmentTransaction transaction;
    private Fragment countFragment;
    private Map<String , Fragment> fragmentMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        setNavOnClick();
        setFrameLayout(fragmentMap.get("首页"));
    }
    private void setNavOnClick() {
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.sy:
                        break;
                    case R.id.hd:
                        break;
                    case R.id.mzyy:
                        break;
                    case R.id.zhyl:
                        break;
                    case R.id.grzx:
                        break;
                }
                return true;
            }
        });
    }

    private void setFrameLayout(Fragment fragment){
        transaction = getSupportFragmentManager().beginTransaction();
        if (fragment.isAdded()){
            transaction.hide(countFragment).show(fragment);
        }else {
            if (countFragment != null){
                transaction.hide(countFragment);
            }
            transaction.add(R.id.frame_layout , fragment , fragment.getTag());
        }
        countFragment = fragment;
        transaction.commit();
    }

    private void initView() {
        countFragment = new Fragment();
        frameLayout = findViewById(R.id.frame_layout);
        nav = findViewById(R.id.nav);
        fragmentMap = new HashMap<>();
        fragmentMap.put("首页" , new SYFragment());
    }
}