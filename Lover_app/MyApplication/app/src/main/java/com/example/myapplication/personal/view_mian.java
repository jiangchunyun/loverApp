package com.example.myapplication.personal;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.myapplication.Listener;
import com.example.myapplication.MyFragment;
import com.example.myapplication.R;
import com.example.myapplication.Service;
import com.example.myapplication.love_people;
import com.example.myapplication.user;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 上官刀刀 on 2017/3/1.
 */

public class view_mian extends Activity implements View.OnClickListener,AMapLocationListener{
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption = null;
    private Button location;
    public static Double q1,q2;
    private Button personal_main;
    private Button main_shuo;
    private List<user> list1;
    private location location_fragment=new location();
    private love_people love_people=new love_people();
    private personal_main personal_main_fragment=new personal_main();
    private FragmentManager fragmentManager=getFragmentManager();
    private FragmentTransaction begain;
    private all_pace all_pace=new all_pace();
    private long exitTime = 0;
    private Button add;
    private TextView main_shou,main_shuoshuo,main_liaotian,main_wode;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_main);
       main_shuo= (Button) findViewById(R.id.main_shuo);
        main_shou= (TextView) findViewById(R.id.main_shou2);
        add= (Button) findViewById(R.id.add_mian);
        main_liaotian= (TextView) findViewById(R.id.main_liaotian);
        main_wode= (TextView) findViewById(R.id.main_wode);
        main_shuoshuo= (TextView) findViewById(R.id.main_shuoshuo);
        mlocationClient = new AMapLocationClient(this);
        mLocationOption = new AMapLocationClientOption();
        mlocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setMockEnable(true);
        mLocationOption.setOnceLocation(true);
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();
        location=(Button) findViewById(R.id.location);
        personal_main=(Button) findViewById(R.id.personal_main);
        begain=fragmentManager.beginTransaction();
        begain.add(R.id.view_main,location_fragment);
        begain.add(R.id.view_main,personal_main_fragment);
        begain.add(R.id.view_main,all_pace);
        begain.hide(personal_main_fragment);
        begain.hide(all_pace);
        begain.show(location_fragment);
        begain.commit();
        location.setBackgroundResource(R.drawable.p_shouye);
        main_shuo.setBackgroundResource(R.drawable.p_kongjie);
        personal_main.setBackgroundResource(R.drawable.p_gereng);
        location.setOnClickListener(this);
        personal_main.setOnClickListener(this);
        main_shuo.setOnClickListener(this);
        main_shou.setOnClickListener(this);
        main_shuoshuo.setOnClickListener(this);
        main_wode.setOnClickListener(this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplication(),good_times.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.location: {
                begain = fragmentManager.beginTransaction();
                begain.hide(personal_main_fragment);
                begain.show(location_fragment);
                begain.hide(all_pace);
                begain.commit();
                location.setBackgroundResource(R.drawable.p_shouye);
                main_shuo.setBackgroundResource(R.drawable.p_kongjie);
                personal_main.setBackgroundResource(R.drawable.p_gereng);
                break;
            }
            case R.id.main_shou2: {
                begain = fragmentManager.beginTransaction();
                begain.hide(personal_main_fragment);
                begain.show(location_fragment);
                begain.hide(all_pace);
                begain.commit();
                location.setBackgroundResource(R.drawable.p_shouye);
                main_shuo.setBackgroundResource(R.drawable.p_kongjie);
                personal_main.setBackgroundResource(R.drawable.p_gereng);
                break;
            }
            case R.id.personal_main: {
                begain = fragmentManager.beginTransaction();
                begain.hide(location_fragment);
                begain.hide(all_pace);
                begain.show(personal_main_fragment);
                begain.commit();
                location.setBackgroundResource(R.drawable.p_shouye2);
                main_shuo.setBackgroundResource(R.drawable.p_kongjie);
                personal_main.setBackgroundResource(R.drawable.p_gereng2);
                break;
            }
            case R.id.main_wode: {
                begain = fragmentManager.beginTransaction();
                begain.hide(location_fragment);
                begain.hide(all_pace);
                begain.show(personal_main_fragment);
                begain.commit();
                location.setBackgroundResource(R.drawable.p_shouye2);
                main_shuo.setBackgroundResource(R.drawable.p_kongjie);
                personal_main.setBackgroundResource(R.drawable.p_gereng2);
                break;
            }
            case R.id.main_shuoshuo: {
                if(love_people.name_2.equals(""))
                {
                    Toast.makeText(getApplication(),"请在个人中心绑定情侣",Toast.LENGTH_LONG).show();

                }
                else {
                    location.setBackgroundResource(R.drawable.p_shouye2);
                    main_shuo.setBackgroundResource(R.drawable.p_kongjie2);
                    personal_main.setBackgroundResource(R.drawable.p_gereng);
                    begain = fragmentManager.beginTransaction();
                    begain.hide(personal_main_fragment);
                    begain.hide(location_fragment);
                    begain.show(all_pace);
                    begain.commit();
                }
                    break;

            }
            case R.id.main_shuo: {
                if(love_people.name_2.equals(""))
                {
                    Toast.makeText(getApplication(),"请在个人中心绑定情侣",Toast.LENGTH_LONG).show();

                }
                else {
                    location.setBackgroundResource(R.drawable.p_shouye2);
                    main_shuo.setBackgroundResource(R.drawable.p_kongjie2);
                    personal_main.setBackgroundResource(R.drawable.p_gereng);
                    begain = fragmentManager.beginTransaction();
                    begain.hide(personal_main_fragment);
                    begain.hide(location_fragment);
                    begain.show(all_pace);
                    begain.commit();
                }
                break;
            }
        }
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
                // Toast.makeText(MainActivity.this,a,Toast.LENGTH_LONG).show();
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
