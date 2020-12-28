package com.example.tiku_b.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tiku_b.AppClient;
import com.example.tiku_b.R;
import com.example.tiku_b.activity.HomeActivity;
import com.example.tiku_b.dialog.WLSZDialog;

public class YinDaoYeFragment extends Fragment {
    private ImageView imageView;
    private Button wlsz;
    private Button jrzy;
    private String string;
    private int tu;
    public YinDaoYeFragment(int tu , String string){
        this.tu = tu;
        this.string = string;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.yin_dao_layout, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
        imageView.setImageResource(tu);
        if (!string.equals("end")){
            wlsz.setVisibility(View.GONE);
            jrzy.setVisibility(View.GONE);
        }else {
            wlsz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new WLSZDialog().show(getFragmentManager() , this.getClass().getName());
                }
            });

            jrzy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppClient.getSharedPreferences().edit().putBoolean("YD" , true).apply();
                    getActivity().finish();
                    getActivity().startActivity(new Intent(getContext() , HomeActivity.class));
                }
            });
        }
    }

    private void initView(View view) {
        imageView = view.findViewById(R.id.image_view);
        wlsz = view.findViewById(R.id.wlsz);
        jrzy = view.findViewById(R.id.jrzy);
    }
}
