package com.example.tiku_b.net;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public interface OkHttpLo {
    void onResponse(Call call, JSONObject jsonObject);
    void onFailure(Call call, IOException e);
}
