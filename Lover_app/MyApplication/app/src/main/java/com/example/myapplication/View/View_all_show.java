package com.example.myapplication.View;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.myapplication.Hotel.Hotel_Adapter;
import com.example.myapplication.Hotel.hotel_item;
import com.example.myapplication.Listener;
import com.example.myapplication.R;
import com.example.myapplication.Service;
import com.example.myapplication.Web_view.Web_item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 上官刀刀 on 2017/9/27.
 */
public class View_all_show extends Activity implements SwipeRefreshLayout.OnRefreshListener, AMapLocationListener {
    private ListView view_list;
    private TextView search_view;
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption = null;
    private static final int REFRESH_COMPLETE = 0X110;
    private Handler mHandler;
    private String city;
    private SwipeRefreshLayout mSwipeLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_show);
        view_list = (ListView) findViewById(R.id.view_list);
        view_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplication(), Web_item.class);
                TextView url=(TextView)view.findViewById(R.id.url);
                intent.putExtra("url",url.getText().toString());
                startActivity(intent);
            }
        });
        search_view = (TextView) findViewById(R.id.search_view);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
        mSwipeLayout.setOnRefreshListener(this);
        mlocationClient = new AMapLocationClient(this);
        mLocationOption = new AMapLocationClientOption();
        mlocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setMockEnable(true);
        mLocationOption.setOnceLocation(true);
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();
        mHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case REFRESH_COMPLETE:
                        Service service = new Service();
                        RequestParams params = new RequestParams();
                        params.put("city", city);
                        service.get(getApplication(), "showallView.php", params, new Listener() {
                            public void Success(String str) {
                                Log.i("8855555", str);
                                //获取电影的爬虫数据
                                List<view_item> list = new ArrayList<view_item>();
                                Gson gson = new Gson();
                                list = gson.fromJson(str, new TypeToken<List<view_item>>() {
                                }.getType());
                                Log.i("8855555", list.get(0).getName());
                                //电影适配器
                                view_list.setAdapter(new View_Adapter(getApplication(), list));
                            }

                            public void Error() {

                            }
                        });
                        mSwipeLayout.setRefreshing(false);
                        break;
                }
            }

            ;
        };

    }

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            //  amapLocation1=amapLocation;
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                String a = amapLocation.getAddress();
                city = amapLocation.getCity();
                Log.i("9999", city);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                Service service = new Service();
                RequestParams params = new RequestParams();
                params.put("city", city);
                service.get(getApplication(), "showallView.php", params, new Listener() {
                    public void Success(String str) {
                        Log.i("8855555", str);
                        //获取电影的爬虫数据
                        List<view_item> list = new ArrayList<view_item>();
                        Gson gson = new Gson();
                        list = gson.fromJson(str, new TypeToken<List<view_item>>() {
                        }.getType());
                        Log.i("8855555", list.get(0).getName());
                        //电影适配器
                        view_list.setAdapter(new View_Adapter(getApplication(), list));
                    }

                    public void Error() {

                    }
                });

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }
}
