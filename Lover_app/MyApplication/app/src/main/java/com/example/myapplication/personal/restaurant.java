package com.example.myapplication.personal;

import android.app.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.overlay.PoiOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 上官刀刀 on 2017/3/12.
 */

public class restaurant extends AppCompatActivity implements AMapLocationListener,PoiSearch.OnPoiSearchListener {
    private MapView mapView;
    private AMap aMap;
    private Button next;
    private Button former;
    private TextView textView;
    private AMapLocation amapLocation1;
    public AMapLocationClient mlocationClient;
    private Button button;
    private String city;
    private int sum;
    private int max;
    public AMapLocationClientOption mLocationOption = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);
        sum=0;
        mapView=(MapView) findViewById(R.id.map);

        mapView.onCreate(savedInstanceState);
        aMap=mapView.getMap();
        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        next= (Button) findViewById(R.id.next);
        former= (Button) findViewById(R.id.former);
        former.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sum<=0)
                {
                    Toast.makeText(getApplication(),"这是第一页",Toast.LENGTH_LONG).show();
                    search(0);
                }
                else {
                    aMap.clear();
                    sum--;
                    search(sum);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aMap.clear();
                sum++;
                search(sum);
            }
        });
        aMap=mapView.getMap();

               /* PolylineOptions p=new PolylineOptions();
                p.add(new LatLng(amapLocation1.getLatitude(),amapLocation1.getLongitude()),new LatLng(39.0,116.0));
                p.color(Color.BLUE);
                p.width(10);
                aMap.addPolyline(p);*/

        textView=(TextView) findViewById(R.id.textmap);
        mlocationClient = new AMapLocationClient(this);
        mLocationOption = new AMapLocationClientOption();
        mlocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setMockEnable(true);
        mLocationOption.setOnceLocation(true);
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();
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
            amapLocation1=amapLocation;
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                String a = amapLocation.getAddress();
                city=amapLocation.getCity();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                //  Toast.makeText(MainActivity.this,a,Toast.LENGTH_LONG).show();
                textView.setText(a);
                search(0);
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(amapLocation1.getLatitude(),amapLocation1.getLongitude()), 19));
                MarkerOptions markerOptions=new MarkerOptions();
                LatLng x = new LatLng(amapLocation1.getLatitude(),amapLocation1.getLongitude());
                markerOptions.position(x);
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.l_weizhi2));
                aMap.addMarker(markerOptions);
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }

    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if(i!=0)
        {
            List<PoiItem> list=poiResult.getPois();
            PoiOverlay poiOverlay = new PoiOverlay(aMap, list);
            poiOverlay.removeFromMap();
            poiOverlay.addToMap();
            poiOverlay.zoomToSpan();
            max=list.size();
            if(max<=0)
            {

                Toast.makeText(getApplication(),"没有了",Toast.LENGTH_LONG).show();
                sum--;
            }
            for(int q=0;q<list.size();q++)
            {
                PoiItem item=list.get(q);
                Log.i("111",item.toString());

            }
        }

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
    public  void search(int i)
    {

        PoiSearch.Query query=new PoiSearch.Query("餐饮服务","");
        query.setPageSize(10);

            max=max-10;
            query.setPageNum(i);
            PoiSearch poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            LatLonPoint point = new LatLonPoint(amapLocation1.getLatitude(), amapLocation1.getLongitude());
            poiSearch.setBound(new PoiSearch.SearchBound(point, 400, true));
            poiSearch.searchPOIAsyn();


    }
}
