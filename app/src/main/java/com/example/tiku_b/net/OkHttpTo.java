package com.example.tiku_b.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.example.tiku_b.AppClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpTo extends Thread{
    private String Url = "http://"+
            AppClient.getSharedPreferences().getString("IP" , "81.70.194.43")+":"+
            AppClient.getSharedPreferences().getString("DK" , "8080")+"/mobileA/";
    private JSONObject jsonObject = new JSONObject();
    private OkHttpLo okHttpLo;
    private boolean isLoop;
    private int time;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (progressDialog != null && progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            if (msg.what == 1){
                okHttpLo.onResponse(null , (JSONObject) msg.obj);
            }else if (msg.what == 2){
                okHttpLo.onFailure(null , (IOException) msg.obj);
            }
            return false;
        }
    });

    public OkHttpTo setUrl(String url){
        Url += url;
        return this;
    }

    public OkHttpTo setJSONObject(String k , Object v){
        try {
            jsonObject.put(k, v);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public OkHttpTo setOkHttpLo(OkHttpLo okHttpLo){
        this.okHttpLo = okHttpLo;
        return this;
    }

    public OkHttpTo setLoop(boolean loop){
        isLoop = loop;
        return this;
    }

    public OkHttpTo setTime(int time){
        this.time = time;
        return this;
    }

    public OkHttpTo setDialog(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("网络请求中。。。");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        return this;
    }

    @Override
    public void run() {
        super.run();
        do {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(100000, TimeUnit.SECONDS)
                    .readTimeout(100000, TimeUnit.SECONDS)
                    .writeTimeout(100000, TimeUnit.SECONDS)
                    .build();
            RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8") , jsonObject.toString());
            Request request = new Request.Builder()
                    .url(Url)
                    .post(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Message message = new Message();
                    message.what = 2;
                    message.obj = e;
                    if (progressDialog != null){
                        handler.sendMessageDelayed(message , 1000);
                    }else {
                        handler.sendMessage(message);
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Message message = new Message();
                    message.what = 1;
                    try {
                        message.obj = new JSONObject(response.body().string());
                        if (progressDialog != null){
                            handler.sendMessageDelayed(message , 1000);
                        }else {
                            handler.sendMessage(message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }while (isLoop);
    }
}
