package com.example.tiku_b.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.tiku_b.AppClient;
import com.example.tiku_b.R;

public class WLSZDialog extends DialogFragment implements View.OnClickListener {
    private EditText ip;
    private EditText dk;
    private Button qx;
    private Button qd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(false);
        return inflater.inflate(R.layout.lig_in_dialog, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());

    }

    private void initView(View view) {
        ip = view.findViewById(R.id.ip);
        ip.setText(AppClient.getSharedPreferences().getString("IP" , "81.70.194.43"));
        dk = view.findViewById(R.id.dk);
        dk.setText(AppClient.getSharedPreferences().getString("DK" , "8080"));
        qx = view.findViewById(R.id.qx);
        qx.setOnClickListener(this);
        qd = view.findViewById(R.id.qd);
        qd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.qx:
                getDialog().dismiss();
                break;
            case R.id.qd:
                getDialog().dismiss();
                AppClient.getSharedPreferences()
                        .edit()
                        .putString("IP" , ip.getText().toString().trim())
                        .putString("DK" , dk.getText().toString().trim())
                        .apply();
                break;
            default:
        }


        
    }
}
