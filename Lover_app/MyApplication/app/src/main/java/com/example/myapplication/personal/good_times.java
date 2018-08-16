package com.example.myapplication.personal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.example.myapplication.Listener;
import com.example.myapplication.R;
import com.example.myapplication.Service;
import com.example.myapplication.love_people;
import com.loopj.android.http.RequestParams;
import com.zzti.fengyongge.imagepicker.PhotoSelectorActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.loopj.android.http.AsyncHttpClient.log;

/**
 * Created by 上官刀刀 on 2017/3/4.
 */

public class good_times extends Activity implements AMapLocationListener {
    private MapView mapView;
    private AMap aMap;
    private AMapLocation amapLocation1;
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption = null;
    private double x;
    private double y;
    private Button camera;
    private EditText things;
    private String location;
    private TextView text_top;
    private Date date;
    private GridView gridView;
    private TextView picture;
    private  RequestParams params;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.good_times);
        gridView= (GridView) findViewById(R.id.id_gradview);
        camera=(Button) findViewById(R.id.camera);
        picture= (TextView) findViewById(R.id.picture);
        things= (EditText) findViewById(R.id.things);
        text_top= (TextView) findViewById(R.id.pppppp);
        params = new RequestParams();
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), PhotoSelectorActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("limit", 10 );//number是选择图片的数量
                startActivityForResult(intent, 0);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*date=new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Toast.makeText(getApplication(), sdf.format(date).toString(),Toast.LENGTH_LONG).show();*/
                if(things.getText().toString().equals(""))
                {
                    Toast.makeText(getApplication(), "说点什么吧...亲爱的",Toast.LENGTH_LONG).show();
                }
                else {
                    Service service = new Service();
                    params.put("name", love_people.name_1);
                    params.put("lovername", love_people.name_2);
                    params.put("x", x);
                    params.put("y", y);
                    params.put("things", things.getText());
                    params.put("location", location);
                    service.get(getApplication(), "live.php", params, new Listener() {
                        public void Success(String str) {
                            if (str.equals("1"))
                            {
                                Toast.makeText(getApplication(), "小宝贝你成功的发表了一个说说", Toast.LENGTH_SHORT).show();
                            }
                             else if(str.equals("-1"))
                            {
                                Toast.makeText(getApplication(), "数据库错误无法保存信息", Toast.LENGTH_SHORT).show();
                            }
                            else if(str.equals("-2"))
                            {
                                Toast.makeText(getApplication(), "图片格式不符合规则", Toast.LENGTH_SHORT).show();
                            }



                        }

                        public void Error() {

                        }
                    });
                }
            }
        });
        mlocationClient = new AMapLocationClient(getApplication());
        mLocationOption = new AMapLocationClientOption();
        mlocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setMockEnable(true);
        mLocationOption.setOnceLocation(true);
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();
        mapView=(MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        aMap=mapView.getMap();
        aMap.setTrafficEnabled(true);
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);



    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            //  amapLocation1=amapLocation;
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                x=amapLocation.getLatitude();//获取纬度
                y=amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                location= amapLocation.getAddress();
                text_top.setText(location);
                //     city=amapLocation.getCity();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间

                // Toast.makeText(getApplication(),location, Toast.LENGTH_SHORT).show();
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(x, y), 19));
                MarkerOptions markerOptions=new MarkerOptions();
                LatLng x1 = new LatLng(x, y);
                markerOptions.position(x1);
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.l_dingwei));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (data != null) {
                    List<String> paths = (List<String>) data.getExtras().getSerializable("photos");//path是选择拍照或者图片的地址数组
                    gridView.setAdapter(new MyAdapter_picture(getApplication(), paths));
                    for(int i=0;i<paths.size();i++) {
                        try {
                            params.put("picture"+String.valueOf(i), new File(paths.get(i)));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                }
                break;
            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
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
