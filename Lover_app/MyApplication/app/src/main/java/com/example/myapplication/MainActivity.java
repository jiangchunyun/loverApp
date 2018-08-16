package com.example.myapplication;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.AMap;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.myapplication.personal.view_mian;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

public class MainActivity extends AppCompatActivity implements AMapLocationListener {
    private MapView mapView;
    private AMap aMap;
    public static Double q1, q2;
    private TextView textView;
    private AMapLocation amapLocation1;
    public AMapLocationClient mlocationClient;
    private Button button;
    private Button button1;
    private Button button2;
    private String city;
    private List<user> list1;
    public AMapLocationClientOption mLocationOption = null;
    private GeocodeSearch geocoderSearch;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Service service = new Service();
        RequestParams params = new RequestParams();
        params.put("name", love_people.name_1);
        service.get(getApplication(), "judge.php", params, new Listener() {
            public void Success(String str) {
                Log.i("1111", str);
                if (str.length() > 5) {
                    for (int i = 0; i < str.length(); i++) {
                        if (str.charAt(i) == ']') {
                            Log.i("1111", str.subSequence(0, i + 1).toString());
                            list1 = new ArrayList<user>();
                            Gson gson = new Gson();
                            list1 = gson.fromJson(str.subSequence(0, i + 1).toString(), new TypeToken<List<user>>() {
                            }.getType());
                            love_people.name_1 = list1.get(0).getName().toString();
                            love_people.x1 = Double.valueOf(list1.get(0).getX());
                            love_people.y1 = Double.valueOf(list1.get(0).getY());
                            //Toast.makeText(getActivity(), love_people.name_1, Toast.LENGTH_SHORT).show();
                            Log.i("2222", str.subSequence(i + 1, str.length()).toString());
                            list1 = gson.fromJson(str.subSequence(i + 1, str.length()).toString(), new TypeToken<List<user>>() {
                            }.getType());
                            love_people.name_2 = list1.get(0).getName().toString();
                            love_people.x2 = Double.valueOf(list1.get(0).getX());
                            love_people.y2 = Double.valueOf(list1.get(0).getY());
                            //  Toast.makeText(getActivity(), love_people.name_2, Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }

                }
                // Toast.makeText(getActivity(), love_people.name_2, Toast.LENGTH_SHORT).show();

                //如果有情侣我们就给live—people的全局变量赋值如果没有我们跳转到绑定情侣的界面
            }

            public void Error() {

            }
        });
        mlocationClient = new AMapLocationClient(this);
        mLocationOption = new AMapLocationClientOption();
        mlocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setMockEnable(true);
        mLocationOption.setOnceLocation(true);
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();
        textView = (TextView) findViewById(R.id.pppppp);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        aMap.setTrafficEnabled(true);
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setBackgroundResource(R.drawable.button_style2);
                button1.setBackgroundResource(R.drawable.button_style);
                button2.setBackgroundResource(R.drawable.button_style);
                geocoderSearch = new GeocodeSearch(getApplication());
                geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
                    @Override
                    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                        textView.setText(regeocodeResult.getRegeocodeAddress().getFormatAddress());
                        Toast.makeText(getApplication(), regeocodeResult.getRegeocodeAddress().getFormatAddress(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

                    }
                });
                RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(love_people.x1, love_people.y1), 200, GeocodeSearch.AMAP);
               aMap.clear();
                geocoderSearch.getFromLocationAsyn(query);
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(love_people.x1, love_people.y1), 19));
                MarkerOptions markerOptions = new MarkerOptions();
                LatLng x = new LatLng(love_people.x1, love_people.y1);
                markerOptions.position(x);
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.l_weizhi2));
                aMap.addMarker(markerOptions);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setBackgroundResource(R.drawable.button_style);
                button1.setBackgroundResource(R.drawable.button_style2);
                button2.setBackgroundResource(R.drawable.button_style);
                geocoderSearch = new GeocodeSearch(getApplication());
                geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
                    @Override
                    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                        textView.setText(regeocodeResult.getRegeocodeAddress().getFormatAddress());
                        Toast.makeText(getApplication(), regeocodeResult.getRegeocodeAddress().getFormatAddress(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

                    }
                });
                aMap.clear();
                RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(love_people.x2, love_people.y2), 200, GeocodeSearch.AMAP);
                geocoderSearch.getFromLocationAsyn(query);
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(love_people.x2, love_people.y2), 19));
                MarkerOptions markerOptions = new MarkerOptions();
                LatLng x = new LatLng(love_people.x2, love_people.y2);
                markerOptions.position(x);
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.l_weizhi2));
                aMap.addMarker(markerOptions);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setBackgroundResource(R.drawable.button_style);
                button1.setBackgroundResource(R.drawable.button_style);
                button2.setBackgroundResource(R.drawable.button_style2);
                LatLng start = new LatLng(love_people.x1, love_people.y1);
                LatLng end = new LatLng(love_people.x2, love_people.y2);
                Float a = AMapUtils.calculateLineDistance(start, end);
                textView.setText("你和你伴侣之间的距离是" + a.toString() + "米");
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
                //     city=amapLocation.getCity();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                //
                q1 = amapLocation.getLatitude();
                q2 = amapLocation.getLongitude();
                //  Toast.makeText(getApplication(),q1 .toString()+" "+q2.toString()+" "+ com.example.myapplication.love_people.name_1,Toast.LENGTH_SHORT).show();
                Service service1 = new Service();
                RequestParams params = new RequestParams();
                params.put("username", com.example.myapplication.love_people.name_1);
                params.put("userx", q1);
                params.put("usery", q2);
                service1.get(getApplication(), "update.php", params, new Listener() {
                    public void Success(String str) {
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
