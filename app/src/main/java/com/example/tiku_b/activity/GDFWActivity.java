package com.example.tiku_b.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiku_b.R;
import com.example.tiku_b.adapter.YyfwGridViewAdapter;
import com.example.tiku_b.bean.Service;
import com.example.tiku_b.net.OkHttpLo;
import com.example.tiku_b.net.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class GDFWActivity extends AppCompatActivity {
    private List<String> serviceType;
    private List<Service> serviceList1;
    private Map<String, Integer> weightMap;
    private TextView fanHui;
    private TextView head;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gdfw_activity);
        initView();
        getAllServiceType();
    }

    /**
     * @effect 获取服务类型
     */
    private void getAllServiceType() {
        new OkHttpTo().setUrl("getAllServiceType")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        serviceType = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString(),
                                new TypeToken<List<String>>() {
                                }.getType());
                        getServiceByType();
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
    }

    /**
     * @effect 根据服务类型获取服务
     */
    private void getServiceByType() {
        serviceList1 = new ArrayList<>();
        for (final String s : serviceType) {
            new OkHttpTo().setUrl("getServiceByType")
                    .setJSONObject("serviceType", s)
                    .setOkHttpLo(new OkHttpLo() {
                        @Override
                        public void onResponse(Call call, JSONObject jsonObject) {
                            serviceList1.addAll((Collection<? extends Service>) new Gson()
                                    .fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString(),
                                            new TypeToken<List<Service>>() {
                                            }.getType()));

                            if (serviceType.get(serviceType.size() - 1)
                                    .equals(serviceList1.get(serviceList1.size() - 1).getServiceType())) {
                                getServiceInOrder();
                            }

                        }

                        @Override
                        public void onFailure(Call call, IOException e) {

                        }
                    }).start();
        }
    }

    /**
     * @effect 获取服务权重
     */
    private void getServiceInOrder() {
        weightMap = new HashMap<>();
        new OkHttpTo().setUrl("getServiceInOrder")
                .setJSONObject("order", 1)
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        JSONArray jsonArray = jsonObject.optJSONArray("ROWS_DETAIL");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                            weightMap.put(jsonObject1.optString("id"), jsonObject1.optInt("weight"));
                        }
                        disposeServiceData();
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
    }

    /**
     * @effect 1、为服务设置权重
     * 2、根据权重排序
     */
    private void disposeServiceData() {
        for (Service s : serviceList1) {
            s.setWeight(weightMap.get(s.getServiceid() + ""));
        }

        Collections.sort(serviceList1, new Comparator<Service>() {
            @Override
            public int compare(Service o1, Service o2) {
                return o2.getWeight() - o1.getWeight();
            }
        });
        showGridView();
    }

    private void showGridView() {
        gridView.setAdapter(new YyfwGridViewAdapter(this , serviceList1));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void initView() {
        fanHui = findViewById(R.id.fan_hui);
        head = findViewById(R.id.head);
        head.setText("更多服务");
        gridView = findViewById(R.id.grid_view);
        fanHui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}