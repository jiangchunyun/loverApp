package com.example.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.myapplication.personal.view_mian;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
/**
 * Created by 上官刀刀 on 2017/1/14.
 */
public class love_people extends Activity implements AMapLocationListener{
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption = null;
    private EditText editText1;
    private EditText editText2;
    private Button button;
    private List<base_x_y> list;
   public static Double x1,q1,q2;
    public static  Double x2;
    public static  Double y1;
    public static  Double y2;
    public static String name_1;
    public static String name_2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.love_people);
        mlocationClient = new AMapLocationClient(this);
        mLocationOption = new AMapLocationClientOption();
        mlocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setMockEnable(true);
        mLocationOption.setOnceLocation(true);
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();
        editText1=(EditText) findViewById(R.id.text1);
       // editText2=(EditText) findViewById(R.id.edit2_login);
        button=(Button) findViewById(R.id.button_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Service service=new Service();
                RequestParams params=new RequestParams();
              //  Toast.makeText(getApplication(), MyFragment.name+editText1.getText().toString()+editText2.getText().toString(), Toast.LENGTH_SHORT).show();
                params.put("firstname",love_people.name_1);
                params.put("secondname",editText1.getText().toString());
                params.put("password","123");
                service.get(getApplication(), "test.php", params, new Listener() {
                    public void Success(String str) {
                        Log.i("111",str);
                        if(str.length()>5) {


                            list = new ArrayList<base_x_y>();
                            Gson gson = new Gson();
                            list = gson.fromJson(str, new TypeToken<List<base_x_y>>() {
                            }.getType());
                            x1 = Double.valueOf(list.get(0).getX());
                            y1 = Double.valueOf(list.get(0).getY());
                            x2 = Double.valueOf(list.get(1).getX());
                            y2 = Double.valueOf(list.get(1).getY());
                            name_1=list.get(0).getName().toString();
                            name_2=list.get(1).getName().toString();
                            Log.i("1111", x1.toString() + y1.toString() + x2.toString() + y2);
                          //  Toast.makeText(getApplication(), name_1+name_2, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplication(), view_mian.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplication(), "输入的用户名不存在或者她（他）已经有情侣，要专一哦", Toast.LENGTH_SHORT).show();
                        }

                        }


                    public void Error() {

                    }
                });
            }
        });


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
              //  Toast.makeText(getApplication(),q1 .toString()+" "+q2.toString()+" "+MyFragment.name, Toast.LENGTH_SHORT).show();
                Service service1=new Service();
                RequestParams params=new RequestParams();
                params.put("username",MyFragment.name);
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
