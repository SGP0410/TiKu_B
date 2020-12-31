package com.example.tiku_b.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tiku_b.R;
import com.example.tiku_b.utili.RoundRectImage;

public class RmztGridViewAdapter extends ArrayAdapter<String> {

    public RmztGridViewAdapter(@NonNull Context context, @NonNull String[] objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.rmzt_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //"[电影, 国庆专题, 抗肺炎, 烈士纪念日]"
        switch (getItem(position).trim()){
            case "电影":
                holder.imageView.setImageResource(R.mipmap.dianyingzhuti);
                break;
            case "国庆专题":
                holder.imageView.setImageResource(R.mipmap.guoqingzhuti);
                break;
            case "抗肺炎":
                holder.imageView.setImageResource(R.mipmap.kangfeiyanzhuti);
                break;
            case "烈士纪念日":
                holder.imageView.setImageResource(R.mipmap.lieshijinianrizhuti);
                break;
        }

        holder.text.setText(getItem(position).trim());

        return convertView;
    }

    static class ViewHolder{
        private RoundRectImage imageView;
        private TextView text;

        public ViewHolder(View view){
            imageView = view.findViewById(R.id.image_view);
            text = view.findViewById(R.id.text);
        }
    }
}
