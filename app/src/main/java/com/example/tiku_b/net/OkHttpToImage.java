package com.example.tiku_b.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpToImage extends Thread {
    private String Url;
    private OkHttpLoImage okHttpLoImage;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == 1){
                okHttpLoImage.onResponse(null , (Bitmap) msg.obj);
            }else if (msg.what == 2){
                okHttpLoImage.onFailure(null , (IOException) msg.obj);
            }
            return false;
        }
    });

    public OkHttpToImage setUrl(String url) {
        Url = url;
        return this;
    }

    public OkHttpToImage setOkHttpLoImage(OkHttpLoImage okHttpLoImage) {
        this.okHttpLoImage = okHttpLoImage;
        return this;
    }

    @Override
    public void run() {
        super.run();
        if (Url == null){
            return;
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 2;
                message.obj = e;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = new Message();
                message.what = 1;
                InputStream inputStream = response.body().byteStream();//得到图片的流
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                message.obj = bitmap;
                handler.sendMessage(message);
            }
        });
    }
}
