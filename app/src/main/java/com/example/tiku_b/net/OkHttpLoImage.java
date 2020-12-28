package com.example.tiku_b.net;

import android.graphics.Bitmap;

import java.io.IOException;

import okhttp3.Call;

public interface OkHttpLoImage {
    void onResponse(Call call, Bitmap bitmap);
    void onFailure(Call call, IOException e);
}
