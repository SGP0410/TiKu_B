package com.example.tiku_b.utili;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.tiku_b.net.OkHttpLoImage;
import com.example.tiku_b.net.OkHttpTo;
import com.example.tiku_b.net.OkHttpToImage;

import java.io.IOException;

import okhttp3.Call;

public class NetImage extends AppCompatImageView {
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

    private int width = 0 , height = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        path.addRoundRect(0,0,width , height , 40 , 40 , Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }
}
