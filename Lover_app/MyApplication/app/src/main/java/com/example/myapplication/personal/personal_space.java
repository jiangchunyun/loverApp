package com.example.myapplication.personal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.myapplication.Listener;
import com.example.myapplication.R;
import com.example.myapplication.Service;
import com.example.myapplication.love_people;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 上官刀刀 on 2017/3/12.
 */

public class personal_space extends Activity implements SwipeRefreshLayout.OnRefreshListener {
    private List<lover_things> list;
    private ListView gridView;

    private lover_things a;
    private int o;
    private Handler mHandler;
    private SwipeRefreshLayout mSwipeLayout;
    private static final int REFRESH_COMPLETE = 0X110;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_space);
        context = this;
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
        gridView = (ListView) findViewById(R.id.gridview);
        mSwipeLayout.setOnRefreshListener(this);
        mHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case REFRESH_COMPLETE:
                        Service service = new Service();
                        RequestParams params = new RequestParams();
                        params.put("name", love_people.name_1);
                        service.get(getApplication(), "showthings.php", params, new Listener() {
                            public void Success(String str) {

                                if (str.length() > 5) {
                                    list = new ArrayList<lover_things>();
                                    Gson gson = new Gson();
                                    list = gson.fromJson(str, new TypeToken<List<lover_things>>() {
                                    }.getType());
                                    gridView.setAdapter(new MyAdapter_lover_things(context, list, gridView));
                                }
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
        Service service = new Service();
        RequestParams params = new RequestParams();
        params.put("name", love_people.name_1);
        service.get(getApplication(), "showthings.php", params, new Listener() {
            public void Success(String str) {

                if (str.length() > 5) {
                    list = new ArrayList<lover_things>();
                    Gson gson = new Gson();
                    list = gson.fromJson(str, new TypeToken<List<lover_things>>() {
                    }.getType());
                    gridView.setAdapter(new MyAdapter_lover_things(context, list, gridView));
                }
            }

            public void Error() {

            }
        });
    }

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
    }
}
