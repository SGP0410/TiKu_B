package com.example.tiku_b.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tiku_b.R;
import com.example.tiku_b.bean.AllNews;
import com.example.tiku_b.net.OkHttpLo;
import com.example.tiku_b.net.OkHttpTo;
import com.example.tiku_b.utili.NetImage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

public class XwlbListViewAdapter extends ArrayAdapter<AllNews> {

    public XwlbListViewAdapter(@NonNull Context context, @NonNull List<AllNews> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final AllNews allNews = getItem(position);
        final ViewHolder holder;
        convertView = View.inflate(parent.getContext(), R.layout.xwlb_list_item, null);
        holder = new ViewHolder(convertView);
        holder.imageView.setNetImageViewUrl(allNews.getPicture());
        holder.title.setText(allNews.getTitle());
        holder.neiRong.setText(allNews.getAbstractX());

        new OkHttpTo().setUrl("getCommitById")
                .setJSONObject("id" , allNews.getNewsid())
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        final JSONArray jsonArray = jsonObject.optJSONArray("ROWS_DETAIL");
                        new OkHttpTo().setUrl("getNewsInfoById")
                                .setJSONObject("newsid" , allNews.getNewsid())
                                .setOkHttpLo(new OkHttpLo() {
                                    @Override
                                    public void onResponse(Call call, JSONObject jsonObject) {
                                        JSONArray jsonArray1 = jsonObject.optJSONArray("ROWS_DETAIL");
                                        JSONObject jsonObject1 = jsonArray1.optJSONObject(0);
                                        holder.plsFbsj.setText("评论总数："+jsonArray.length() +"    发布时间："+jsonObject1.optString("publicTime"));
                                    }

                                    @Override
                                    public void onFailure(Call call, IOException e) {

                                    }
                                }).start();
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
        return convertView;
    }

    static class ViewHolder{
        private NetImage imageView;
        private TextView title;
        private TextView neiRong;
        private TextView plsFbsj;

        public ViewHolder(View view){
            imageView = view.findViewById(R.id.image_view);
            title = view.findViewById(R.id.title);
            neiRong = view.findViewById(R.id.nei_rong);
            plsFbsj = view.findViewById(R.id.pls_fbsj);
        }
    }
}
