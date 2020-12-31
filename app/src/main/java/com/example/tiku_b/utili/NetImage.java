package com.example.tiku_b.utili;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tiku_b.net.OkHttpLoImage;
import com.example.tiku_b.net.OkHttpToImage;

import java.io.IOException;

import okhttp3.Call;

public class NetImage extends androidx.appcompat.widget.AppCompatImageView {
    public NetImage(@NonNull Context context) {
        super(context);
    }

    public NetImage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NetImage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setNetImageViewUrl(String url){
        new OkHttpToImage().setUrl(url)
                .setOkHttpLoImage(new OkHttpLoImage() {
                    @Override
                    public void onResponse(Call call, Bitmap bitmap) {
                        setImageBitmap(bitmap);
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
    }
}
