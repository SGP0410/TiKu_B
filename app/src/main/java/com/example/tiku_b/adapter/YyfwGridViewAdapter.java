package com.example.tiku_b.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tiku_b.R;
import com.example.tiku_b.bean.Service;
import com.example.tiku_b.utili.OvalImageView;

import java.util.List;

public class YyfwGridViewAdapter extends ArrayAdapter<Service> {

    public YyfwGridViewAdapter(@NonNull Context context, @NonNull List<Service> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        Service service = getItem(position);
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.yyfw_grid_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (service.getIcon().equals(R.mipmap.gdfw+"")) {
            holder.imageView.setImageResource(R.mipmap.gdfw);
        }else {
            holder.imageView.setNetImageViewUrl(service.getIcon());
        }

        holder.text.setText(service.getServiceName());

        return convertView;
    }

    static class ViewHolder{
        private OvalImageView imageView;
        private TextView text;

        public ViewHolder(View view){
            imageView = view.findViewById(R.id.image_view);
            text = view.findViewById(R.id.text);
        }
    }
}
