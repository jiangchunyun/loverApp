package com.example.myapplication.Movie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Listener;
import com.example.myapplication.MyFragment;
import com.example.myapplication.R;
import com.example.myapplication.Service;
import com.example.myapplication.Web_view.Web_item;
import com.example.myapplication.love_people;
import com.example.myapplication.personal.MyAdapter_lover_things;
import com.example.myapplication.personal.lover_things;
import com.example.myapplication.personal.tv;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 上官刀刀 on 2017/9/27.
 */
public class Movie_all_show extends Activity implements SwipeRefreshLayout.OnRefreshListener {
    private ListView movie_list;
    private TextView search_movie;
    private static final int REFRESH_COMPLETE = 0X110;
    private Handler mHandler;
    private SwipeRefreshLayout mSwipeLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_all_show);
        movie_list = (ListView) findViewById(R.id.movie_list);
        movie_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplication(), Web_item.class);
                TextView url=(TextView)view.findViewById(R.id.url);
                intent.putExtra("url",url.getText().toString());
                startActivity(intent);
            }
        });
        search_movie = (TextView) findViewById(R.id.search_movie);
        TelephonyManager telephonyManager = (TelephonyManager) getApplication().getSystemService(Context.TELEPHONY_SERVICE);
        search_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), tv.class);
                startActivity(intent);
            }
        });
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
        mSwipeLayout.setOnRefreshListener(this);
        mHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case REFRESH_COMPLETE:
                        Service service = new Service();
                        RequestParams params = new RequestParams();
                        service.get(getApplication(), "showallMovie.php", params, new Listener() {
                            public void Success(String str) {
                                Log.i("8855555", str);
                                //获取电影的爬虫数据
                                List<Movie_item> list = new ArrayList<Movie_item>();
                                Gson gson = new Gson();
                                list = gson.fromJson(str, new TypeToken<List<Movie_item>>() {
                                }.getType());
                                Log.i("8855555", list.get(0).getName());
                                //电影适配器
                                movie_list.setAdapter(new Movie_Adapter(getApplication(), list));
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
        service.get(getApplication(), "showallMovie.php", params, new Listener() {
            public void Success(String str) {
                Log.i("8855555", str);
                //获取电影的爬虫数据
                List<Movie_item> list = new ArrayList<Movie_item>();
                Gson gson = new Gson();
                list = gson.fromJson(str, new TypeToken<List<Movie_item>>() {
                }.getType());
                Log.i("8855555", list.get(0).getName());
                //电影适配器
                movie_list.setAdapter(new Movie_Adapter(getApplication(), list));
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
