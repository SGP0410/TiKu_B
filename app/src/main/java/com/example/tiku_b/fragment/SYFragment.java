package com.example.tiku_b.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.example.tiku_b.R;
import com.example.tiku_b.activity.GDFWActivity;
import com.example.tiku_b.adapter.BqyGridViewAdapter;
import com.example.tiku_b.adapter.RmztGridViewAdapter;
import com.example.tiku_b.adapter.XwlbListViewAdapter;
import com.example.tiku_b.adapter.YyfwGridViewAdapter;
import com.example.tiku_b.bean.AllNews;
import com.example.tiku_b.bean.BQY;
import com.example.tiku_b.bean.LBT;
import com.example.tiku_b.bean.Service;
import com.example.tiku_b.net.OkHttpLo;
import com.example.tiku_b.net.OkHttpTo;
import com.example.tiku_b.utili.MyGridView;
import com.example.tiku_b.utili.MyListView;
import com.example.tiku_b.utili.NetImage;
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


public class SYFragment extends Fragment {
    /**
     * @ init view
     */
    private EditText seek;
    private ViewPager viewPager;
    private MyGridView yyfwGridView;
    private MyGridView rmztGridView;
    private MyGridView bqyGridView;
    private MyListView xwlbListView;
    /**
     * @ 轮播图
     */
    private List<LBT> lbtList;
    private boolean isLunBo;
    private MyLbtThread lbtThread;

    /**
     * @ 应用服务
     */
    private List<String> serviceType;
    private List<Service> serviceList1 , serviceList2;
    private Map<String , Integer> weightMap;

    /**
     * @ 热门主题
     */
    private String[] subjects;

    /**
     * @ 标签页
     */
    private List<BQY> newType;
    private BqyGridViewAdapter bqyGridViewAdapter;

    /**
     * @ 新闻专栏
     */
    private List<AllNews> allNews;
    private Map<String , List<AllNews>> newsMap;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sy_layout, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
        setSeek();
        setViewPager();
        setYyfuGridView();
        setRmztGridView();
        setBqyGridView();
        setXwlbListView();
    }

    private void setSeek() {
        seek.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){

                }
                return true;
            }
        });
    }

    /**
     * @effect 轮播图
     */
    private void setViewPager(){
        getImages();
    }

    /**
     * @effect  获取轮播图图片
     */
    private void getImages() {
        new OkHttpTo().setUrl("getImages")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        lbtList = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString() ,
                                new TypeToken<List<LBT>>(){}.getType());
                        if (lbtList != null){
                            showViewPager();
                        }
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
    }

    /**
     * @effect  设置ViewPager数据
     */
    private void showViewPager() {
        final List<View> views = new ArrayList<>();
        for (final LBT lbt: lbtList) {
            View view = View.inflate(getContext() , R.layout.lbt_layout , null);
            NetImage netImage = view.findViewById(R.id.net_image);
            netImage.setNetImageViewUrl(lbt.getPath());
            netImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LBT lbt1 = lbt;
                }
            });
            views.add(view);
        }
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                if (views != null){
                    return Integer.MAX_VALUE;
                }
                return 0;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                int countPosition = position % views.size();
                container.addView(views.get(countPosition));
                return views.get(countPosition);
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                int countPosition = position % views.size();
                container.removeView(views.get(countPosition));
            }
        });
        viewPager.setCurrentItem(0);
        autoLunBo();
    }

    /**
     * @effect 开启轮播线程
     */
    private void autoLunBo() {
        isLunBo = true;
        if (lbtThread == null){
            lbtThread = new MyLbtThread();
            lbtThread.start();
        }
    }

    /**
     * @effect 当fragment隐藏时，暂停轮播
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden){
            if (lbtThread != null){
                isLunBo = false;
                lbtThread.interrupt();
                lbtThread = null;
            }
        }else {
            autoLunBo();
        }
    }

    /**
     * @effect 使轮播图动起来
     */
    class MyLbtThread extends Thread{
        @Override
        public void run() {
            while (isLunBo){
                try {
                    Thread.sleep(3000);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @effect  应用服务
     */
    private void setYyfuGridView(){
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
                        serviceType = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString() ,
                                new TypeToken<List<String>>(){}.getType());
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
                    .setJSONObject("serviceType" , s)
                    .setOkHttpLo(new OkHttpLo() {
                        @Override
                        public void onResponse(Call call, JSONObject jsonObject) {
                            serviceList1.addAll((Collection<? extends Service>) new Gson()
                                    .fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString() ,
                                    new TypeToken<List<Service>>(){}.getType()));

                            if (serviceType.get(serviceType.size() - 1)
                                    .equals(serviceList1.get(serviceList1.size() - 1).getServiceType())){
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
                .setJSONObject("order" , 1)
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        JSONArray jsonArray = jsonObject.optJSONArray("ROWS_DETAIL");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                            weightMap.put(jsonObject1.optString("id") , jsonObject1.optInt("weight"));
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
     *          2、根据权重排序
     *          3、的到前九个服务
     */
    private void disposeServiceData() {
        for (Service s : serviceList1) {
            s.setWeight(weightMap.get(s.getServiceid()+""));
        }

        Collections.sort(serviceList1, new Comparator<Service>() {
            @Override
            public int compare(Service o1, Service o2) {
                return o2.getWeight() - o1.getWeight();
            }
        });

        serviceList2 = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            serviceList2.add(serviceList1.get(i));
        }
        serviceList2.add(new Service("更多服务" , R.mipmap.gdfw+""));
        showYyfwGridViewByServiceList2();
    }

    private void showYyfwGridViewByServiceList2() {
        yyfwGridView.setAdapter(new YyfwGridViewAdapter(getContext() , serviceList2));
        yyfwGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (serviceList2.get(position).getServiceName().equals("更多服务")){
                    getActivity().startActivity(new Intent(getContext() , GDFWActivity.class));
                }
            }
        });
    }

    /**
     * @effect  热门主题
     */
    private void setRmztGridView(){
        getAllSubject();
    }

    /**
     * @effect 获取所有主题
     */
    private void getAllSubject() {
        new OkHttpTo().setUrl("getAllSubject")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        String string = jsonObject.optString("ROWS_DETAIL");
                        string = string.substring(1 , string.length() - 1);
                        subjects = string.split(",");
                        showRmztGridView();
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
    }

    /**
     * @ 显示热门主题
     */
    private void showRmztGridView() {
        rmztGridView.setAdapter(new RmztGridViewAdapter(getContext() , subjects));
        rmztGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    /**
     * @effect  标签页
     */
    private void setBqyGridView(){
        getAllNewsType();
    }

    /**
     * @effect 获取新闻类别
     */
    private void getAllNewsType() {
        newType = new ArrayList<>();
        new OkHttpTo().setUrl("getAllNewsType")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        newType = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString() ,
                                new TypeToken<List<BQY>>(){}.getType());
                        showBqyGridView();
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
    }

    private void showBqyGridView() {
        newType.get(0).setPitchOn(true);
        bqyGridViewAdapter = new BqyGridViewAdapter(getContext() , newType);
        bqyGridView.setAdapter(bqyGridViewAdapter);
        bqyGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < newType.size(); i++) {
                    if (i == position){
                        newType.get(i).setPitchOn(true);
                        showXwlbListView(newType.get(i).getNewstype().trim());
                    }else {
                        newType.get(i).setPitchOn(false);
                    }
                }
                bqyGridViewAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * @effect  新闻专栏
     */
    private void setXwlbListView(){
        getNEWsList();
    }

    /**
     * @effect 获取全部新闻
     */
    private void getNEWsList() {
        new OkHttpTo().setUrl("getNEWsList")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        allNews = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString() ,
                                new TypeToken<List<AllNews>>(){}.getType());
                        newsClassify();
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
    }

    /**
     * @effect 新闻分类
     */
    private void newsClassify() {
        newsMap = new HashMap<>();
        for (AllNews news: allNews) {
            List<AllNews> allNews1 = newsMap.get(news.getNewsType());
            if (allNews1 == null){
                allNews1 = new ArrayList<>();
                newsMap.put(news.getNewsType() , allNews1);
            }
            allNews1.add(news);
        }
        showXwlbListView(newType.get(0).getNewstype());
    }

    private void showXwlbListView(String type) {
        xwlbListView.setAdapter(new XwlbListViewAdapter(getContext() ,
                newsMap.get(type.trim())));

    }

    private void initView(View view) {
        seek = view.findViewById(R.id.seek);
        viewPager = view.findViewById(R.id.view_pager);
        yyfwGridView = view.findViewById(R.id.yyfw_grid_view);
        rmztGridView = view.findViewById(R.id.rmzt_grid_view);
        bqyGridView = view.findViewById(R.id.bqy_grid_view);
        xwlbListView = view.findViewById(R.id.xwlb_list_view);
    }
}
