package com.example.myapplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.overlay.DrivingRouteOverlay;
import com.amap.api.maps.overlay.WalkRouteOverlay;
import com.amap.api.services.route.RouteSearch.WalkRouteQuery;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RidePath;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static android.R.attr.mode;
/**
 * Created by 上官刀刀 on 2017/2/5.
 */
public class driver_resoult extends Activity implements RouteSearch.OnRouteSearchListener,AMapLocationListener {
    private AMap aMap;
    private MapView mapView;
    public static Double q1,q2;
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption = null;
    private RouteSearch mRouteSearch;
    private DriveRouteResult mDriveRouteResult;
    private WalkRouteResult mWalkRouteResult;
    private String mCurrentCityName = "北京";
    private List<user> list1;
    private List<BusPath> mBusPathList;
    private RideRouteResult mRideRouteResult;
    private BusRouteResult mBusRouteResult;
    private LatLonPoint mStartPoint = new LatLonPoint(39.942295,116.335891);//起点，39.942295,116.335891
    private LatLonPoint mEndPoint = new LatLonPoint(39.995576,116.481288);//终点，39.995576,116.481288
    private Button button_driver,button_walk,button_ride;
    private Button button_driver0,button_walk0,button_ride0;
    private int driver=0,walk=0,ride=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_resoult);
        mlocationClient = new AMapLocationClient(this);
        mLocationOption = new AMapLocationClientOption();
        mlocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setMockEnable(true);
        mLocationOption.setOnceLocation(true);
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();
        Service service=new Service();
        RequestParams params=new RequestParams();
        params.put("name",love_people.name_1);
        service.get(getApplication(), "judge.php", params, new Listener() {
            public void Success(String str) {
                Log.i("1111",str);
                if(str.length()>5) {
                    for (int i = 0; i < str.length(); i++) {
                        if (str.charAt(i) == ']') {
                            Log.i("1111", str.subSequence(0, i + 1).toString());
                            list1 = new ArrayList<user>();
                            Gson gson = new Gson();
                            list1 = gson.fromJson(str.subSequence(0, i + 1).toString(), new TypeToken<List<user>>() {
                            }.getType());
                            love_people.name_1= list1.get(0).getName().toString();
                            love_people.x1= Double.valueOf(list1.get(0).getX());
                            love_people.y1= Double.valueOf(list1.get(0).getY());
                            //Toast.makeText(getActivity(), love_people.name_1, Toast.LENGTH_SHORT).show();
                            Log.i("2222", str.subSequence(i + 1, str.length()).toString());
                            list1 = gson.fromJson(str.subSequence(i + 1, str.length()).toString(), new TypeToken<List<user>>() {
                            }.getType());
                            love_people.name_2 = list1.get(0).getName().toString();
                            love_people.x2= Double.valueOf(list1.get(0).getX());
                            love_people.y2= Double.valueOf(list1.get(0).getY());
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
        button_driver=(Button) findViewById(R.id.button_driver);
        button_ride=(Button) findViewById(R.id.button_ride);
        button_walk= (Button) findViewById(R.id.button_walk);
        button_driver0=(Button) findViewById(R.id.button_driver0);
        button_ride0=(Button) findViewById(R.id.button_ride0);
        button_walk0= (Button) findViewById(R.id.button_walk0);
        mapView = (MapView) findViewById(R.id.route_map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        aMap = mapView.getMap();
        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        mStartPoint = new LatLonPoint(love_people.x1, love_people.y1);
        mEndPoint = new LatLonPoint(love_people.x2, love_people.y2);
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(mStartPoint, mEndPoint);
        RouteSearch.BusRouteQuery query1 = new RouteSearch.BusRouteQuery(fromAndTo, RouteSearch.BusDefault, mCurrentCityName, 0);
        final RouteSearch.WalkRouteQuery query0 = new RouteSearch.WalkRouteQuery(fromAndTo, RouteSearch.WalkDefault);
     //  mRouteSearch.calculateWalkRouteAsyn(query0);// 异步路径规划步行模式查询
        final RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault, null, null, "");
      //  mRouteSearch.calculateDriveRouteAsyn(query);
        final RouteSearch.RideRouteQuery query2 = new RouteSearch.RideRouteQuery(fromAndTo, mode);
        //mRouteSearch.calculateRideRouteAsyn(query2);// 异步路径规划骑行模式查询
        button_ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aMap.clear();
                mRouteSearch.calculateRideRouteAsyn(query2);// 异步路径规划骑行模式查询

            }
        });
        button_walk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aMap.clear();
                mRouteSearch.calculateWalkRouteAsyn(query0);
            }
        });
        button_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aMap.clear();
                mRouteSearch.calculateDriveRouteAsyn(query);
            }
        });
        button_ride0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(ride==1000)
               {
                   Intent intent=new Intent(getApplication(),RideRouteCalculateActivity.class);
                   startActivity(intent);
               }
                else if(ride==0)
               {
                   Toast.makeText(getApplication(),"请先规划路径", Toast.LENGTH_SHORT).show();
               }
                else if(ride==-1000)
               {
                   Toast.makeText(getApplication(),"不适合骑车路径导航", Toast.LENGTH_SHORT).show();
               }

            }
        });
        button_walk0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(walk==1000)
                {
                    Intent intent=new Intent(getApplication(),WalkRouteCalculateActivity.class);
                    startActivity(intent);
                }
                else if(walk==0)
                {
                    Toast.makeText(getApplication(),"请先规划步行路径", Toast.LENGTH_SHORT).show();
                }
                else if(walk==-1000)
                {
                    Toast.makeText(getApplication(),"不适合步行路径导航", Toast.LENGTH_SHORT).show();
                }

            }
        });
        button_driver0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(driver==1000)
                {
                    Intent intent=new Intent(getApplication(),SingleRouteCalculateActivity.class);
                    startActivity(intent);
                }
                else if(driver==0)
                {
                    Toast.makeText(getApplication(),"请先规划开车路径", Toast.LENGTH_SHORT).show();
                }
                else if(driver==-1000)
                {
                    Toast.makeText(getApplication(),"不适合开车路径导航", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }





    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {
    //    mBusRouteResult = busRouteResult;

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
        if(i==1000) {
            driver=1000;
            mDriveRouteResult = driveRouteResult;
            DrivePath drivePath = mDriveRouteResult.getPaths().get(0);
            DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(getApplication(), aMap, drivePath, mDriveRouteResult.getStartPos(), mDriveRouteResult.getTargetPos(), null);
            drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
            //drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
            drivingRouteOverlay.removeFromMap();
            drivingRouteOverlay.addToMap();
            drivingRouteOverlay.zoomToSpan();
        }
        else
        {
            driver=-1000;
            Toast.makeText(getApplication(),"不适宜开车路径", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {
        if(i==1000) {
            walk=1000;
            mWalkRouteResult = walkRouteResult;
            WalkPath walkPath = mWalkRouteResult.getPaths().get(0);
           WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(this, aMap, walkPath, mWalkRouteResult.getStartPos(), mWalkRouteResult.getTargetPos());
            walkRouteOverlay.removeFromMap();
           walkRouteOverlay.addToMap();
            walkRouteOverlay.zoomToSpan();
        }
        else
        {
            walk=-1000;
            Toast.makeText(getApplication(),"不适宜行走路径", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {
        if(i==1000) {
            ride=1000;
            mRideRouteResult = rideRouteResult;
            RidePath ridePath = mRideRouteResult.getPaths().get(0);
            RideRouteOverlay rideRouteOverlay = new RideRouteOverlay(
                    this, aMap, ridePath,
                    mRideRouteResult.getStartPos(),
                    mRideRouteResult.getTargetPos());
            rideRouteOverlay.removeFromMap();
            rideRouteOverlay.addToMap();
            rideRouteOverlay.zoomToSpan();
        }
        else
        {
            ride=-1000;
            Toast.makeText(getApplication(),"不适宜骑车路径", Toast.LENGTH_SHORT).show();
        }

    }
    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
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
                q1=amapLocation.getLatitude();
                q2=amapLocation.getLongitude();
                love_people.x1=q1;
                love_people.y1=q2;
                //  Toast.makeText(getApplication(),q1 .toString()+" "+q2.toString()+" "+ com.example.myapplication.love_people.name_1,Toast.LENGTH_SHORT).show();
                Service service1=new Service();
                RequestParams params=new RequestParams();
                params.put("username", com.example.myapplication.love_people.name_1);
                params.put("userx",q1);
                params.put("usery",q2);
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
