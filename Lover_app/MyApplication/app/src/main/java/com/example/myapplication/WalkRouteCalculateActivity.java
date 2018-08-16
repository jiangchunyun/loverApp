package com.example.myapplication;

import android.os.Bundle;

import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.NaviLatLng;


public class WalkRouteCalculateActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_basic_navi);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);
    }

    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        mAMapNavi.calculateWalkRoute(new NaviLatLng(love_people.x1, love_people.y1), new NaviLatLng(love_people.x2, love_people.y2));
      // mAMapNavi.calculateRideRoute(new NaviLatLng(love_people.x1, love_people.y1), new NaviLatLng(love_people.x2, love_people.y2));
       // super.onCalculateRouteSuccess();
       // mAMapNavi.startNavi(NaviType.EMULATOR);
    }

    @Override
    public void onCalculateRouteSuccess() {
        super.onCalculateRouteSuccess();

        mAMapNavi.startNavi(NaviType.EMULATOR);
    }
}
