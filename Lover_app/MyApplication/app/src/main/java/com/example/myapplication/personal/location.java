package com.example.myapplication.personal;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Food.Food_all_show;
import com.example.myapplication.Hotel.Hotel_all_show;
import com.example.myapplication.Listener;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Movie.*;
import com.example.myapplication.R;
import com.example.myapplication.Service;
import com.example.myapplication.View.View_all_show;
import com.example.myapplication.driver_resoult;
import com.example.myapplication.goodtime_count;
import com.example.myapplication.love_people;
import com.example.myapplication.user;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 上官刀刀 on 2017/3/1.
 */

public class location extends Fragment {

    private Button button_changetomain;
    private Button rote_calculate;
    private TextView name_1_name_2;
    private TextView name_1_name_21;
    private Button time;
    private Button restaurant;
    private Button hotel;
    private Button views;
    private Button tv;
    private Button search;
    private List<lover_pictures> list;
    private ZQImageViewRoundOval picture;
    private ZQImageViewRoundOval picture1;
    private imageloader mimageloader;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.location, container, false);
        button_changetomain = (Button) view.findViewById(R.id.changetomain);
        name_1_name_2 = (TextView) view.findViewById(R.id.name_1_name_2);
        name_1_name_21 = (TextView) view.findViewById(R.id.name_1_name_21);
        rote_calculate = (Button) view.findViewById(R.id.route_calculate);
        restaurant = (Button) view.findViewById(R.id.restaurant);
        hotel = (Button) view.findViewById(R.id.hotel);
        time = (Button) view.findViewById(R.id.times);
        views = (Button) view.findViewById(R.id.views);
        search = (Button) view.findViewById(R.id.search);
        tv = (Button) view.findViewById(R.id.tv);
        picture = (ZQImageViewRoundOval) view.findViewById(R.id.picture);
        picture1 = (ZQImageViewRoundOval) view.findViewById(R.id.picture1);
        mimageloader = new imageloader();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PoiKeywordSearchActivity.class);
                startActivity(intent);
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Movie_all_show.class);
                startActivity(intent);
            }
        });
        views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), View_all_show.class);
                startActivity(intent);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), goodtime_count.class);
                startActivity(intent);

            }
        });
        button_changetomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (love_people.name_2.equals("")) {
                    Toast.makeText(getActivity(), "请在个人中心绑定情侣", Toast.LENGTH_LONG).show();

                } else {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        rote_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (love_people.name_2.equals("")) {
                    Toast.makeText(getActivity(), "请在个人中心绑定情侣", Toast.LENGTH_LONG).show();

                } else {
                    Intent intent = new Intent(getActivity(), driver_resoult.class);
                    startActivity(intent);
                }
            }
        });
        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Food_all_show.class);
                startActivity(intent);
            }
        });
        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Hotel_all_show.class);
                startActivity(intent);
            }
        });
        name_1_name_2.setText(love_people.name_1);
        name_1_name_21.setText(love_people.name_2);
        if (!(love_people.name_2.equals(""))) {
            Service service = new Service();
            RequestParams params = new RequestParams();
            params.put("name1", love_people.name_1);
            params.put("name2", love_people.name_2);
            service.get(getActivity(), "ShowPortrait.php", params, new Listener() {
                public void Success(String str) {
                    list = new ArrayList<lover_pictures>();
                    Gson gson = new Gson();
                    list = gson.fromJson(str, new TypeToken<List<lover_pictures>>() {
                    }.getType());
                    picture.setTag(list.get(0).getPortrait());
                    picture1.setTag(list.get(1).getPortrait());
                    Picasso.with(getActivity()).load(list.get(0).getPortrait()).fit().into(picture);
                    Picasso.with(getActivity()).load(list.get(1).getPortrait()).fit().into(picture1);
                }
                public void Error() {

                }
            });
        }
        return view;
    }
}
