package com.example.tiku_b.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tiku_b.R;
import com.example.tiku_b.bean.BQY;

import java.util.List;

public class BqyGridViewAdapter extends ArrayAdapter<BQY> {

    public BqyGridViewAdapter(@NonNull Context context, @NonNull List<BQY> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = View.inflate(parent.getContext() , android.R.layout.simple_spinner_item , null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(getItem(position).getNewstype());

        if (getItem(position).isPitchOn()){
            holder.textView.setBackgroundResource(R.drawable.bqy_bg);
        }else {
            holder.textView.setBackgroundColor(Color.parseColor("#FDFDFE"));
        }

        return convertView;
    }

    static class ViewHolder{

        private TextView textView;

        public ViewHolder(View view){
            textView = view.findViewById(android.R.id.text1);
            textView.setTextSize(16);
            textView.setTextColor(Color.parseColor("#333333"));
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(0 , 0 , 0 , 10);
        }
    }
}
