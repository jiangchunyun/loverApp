package com.example.myapplication.personal;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Listener;
import com.example.myapplication.MainActivity;
import com.example.myapplication.MyFragment;
import com.example.myapplication.MyFragment2;
import com.example.myapplication.R;
import com.example.myapplication.Service;
import com.example.myapplication.driver_resoult;
import com.example.myapplication.love_people;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 上官刀刀 on 2017/3/1.
 */

public class all_pace extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private List<lover_things> list;
    private ListView gridView;
    private lover_things a;
    private int o;
    private TextView fabiao;
    private Context context;
    private TextView personal;
    private Handler mHandler;
    List<List<comment>> thingcommentList;
    private SwipeRefreshLayout mSwipeLayout;
    private static final int REFRESH_COMPLETE = 0X110;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.all_things, container, false);
        context = getActivity();
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_swipe_ly);
        gridView = (ListView) view.findViewById(R.id.gridview);
        mSwipeLayout.setOnRefreshListener(this);
        mHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case REFRESH_COMPLETE:
                        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                        String imei = telephonyManager.getDeviceId();
                        Service service = new Service();
                        RequestParams params = new RequestParams();
                        params.put("name", love_people.name_1);
                        params.put("mac", imei);
                        service.get(getActivity(), "showallthings.php", params, new Listener() {
                            public void Success(String str) {

                                Log.i("message", str);
                                if (str.equals("404")) {
                                    Toast.makeText(getActivity(), "您的账号在其他设备上登录，您可选择重新登录", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getActivity(), MyFragment.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);

                                } else if (str.length() > 5) {
                                    list = new ArrayList<lover_things>();
                                    Gson gson = new Gson();
                                    list = gson.fromJson(str, new TypeToken<List<lover_things>>() {
                                    }.getType());
                                    gridView.setAdapter(new MyAdapter_lover_things(getActivity(), list, gridView));
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
        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        Service service = new Service();
        RequestParams params = new RequestParams();
        params.put("name", love_people.name_1);
        params.put("mac", imei);
        service.get(getActivity(), "showallthings.php", params, new Listener() {
            public void Success(String str) {

                if (str.equals("404")) {
                    Toast.makeText(getActivity(), "您的账号在其他设备上登录，您可选择重新登录", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), MyFragment.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else if (str.length() > 5) {
                    list = new ArrayList<lover_things>();
                    Gson gson = new Gson();
                    list = gson.fromJson(str, new TypeToken<List<lover_things>>() {
                    }.getType());
                    gridView.setAdapter(new MyAdapter_lover_things(getActivity(), list, gridView));
                }
            }

            public void Error() {

            }
        });
        return view;
    }

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
    }
}
