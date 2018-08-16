package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.example.myapplication.New_class_name.Place_x_y;
import com.example.myapplication.personal.lover_pictures;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 上官刀刀 on 2017/9/21.
 */

public class goodtime_count extends Activity {
    private MapView mapView;
    private AMap aMap;
    private Button all;
    private Button my;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goodtime_count);
        mapView = (MapView) findViewById(R.id.map);
        all= (Button) findViewById(R.id.button);
        my= (Button) findViewById(R.id.button2);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        aMap.setTrafficEnabled(true);
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aMap.clear();
                Service service=new Service();
                RequestParams params=new RequestParams();
                 params.put("name",love_people.name_1);
                Log.i("llllss",love_people.name_1);
                // params.put("name2",love_people.name_2);
                service.get(getApplicationContext(), "showplace.php", params, new Listener() {
                    public void Success(String str) {
                        List <Place_x_y>    list = new ArrayList<Place_x_y>();
                        Gson gson = new Gson();
                        list = gson.fromJson(str, new TypeToken<List<Place_x_y>>() {}.getType());
                        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(love_people.x1, love_people.y1), 10));
                        for(int i=0;i<list.size();i++) {
                            MarkerOptions markerOptions = new MarkerOptions();
                            LatLng x = new LatLng(list.get(i).getX(), list.get(i).getY());
                            markerOptions.position(x);
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.l_weizhi2));
                            aMap.addMarker(markerOptions);
                        }
                    }
                    public void Error() {

                    }
                });
            }
        });
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aMap.clear();
                Service service=new Service();
                RequestParams params=new RequestParams();
               // params.put("name1",love_people.name_1);
               // params.put("name2",love_people.name_2);
                service.get(getApplicationContext(), "showallplace.php", params, new Listener() {
                    public void Success(String str) {
                        List <Place_x_y>    list = new ArrayList<Place_x_y>();
                        Gson gson = new Gson();
                        list = gson.fromJson(str, new TypeToken<List<Place_x_y>>() {}.getType());
                        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(love_people.x2, love_people.y2), 3));
                        for(int i=0;i<list.size();i++) {
                            MarkerOptions markerOptions = new MarkerOptions();
                            LatLng x = new LatLng(list.get(i).getX(), list.get(i).getY());
                            markerOptions.position(x);
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.l_weizhi2));
                            aMap.addMarker(markerOptions);
                        }
                    }
                    public void Error() {

                    }
                });
            }
        });

    }
    protected void onResume() {
        super.onResume();
        mapView.onResume();

    }

    protected void onPause() {
        super.onPause();
        mapView.onPause();

    }

    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();

    }
}
