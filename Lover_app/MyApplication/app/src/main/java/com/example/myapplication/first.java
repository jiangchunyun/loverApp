package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.personal.view_mian;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.R.attr.logo;

/**
 * Created by 上官刀刀 on 2017/1/22.
 */

public class first extends Activity {
    private SharedPreferences sp;
    private String username;
    private String password;
    private List<user> list1;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);
        sp = getSharedPreferences("lover_app", Context.MODE_PRIVATE);
        initGPS();
    }

    class lunchhandler implements Runnable {

        public void run() {
            startActivity(new Intent(getApplication(), MyFragment.class));
            first.this.finish();
        }

    }

    private void initGPS() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        //判断GPS模块是否开启，如果没有则开启
        if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            Toast.makeText(getApplication(), "GPS权限没有开启无法使用此app，请开启定位权限", Toast.LENGTH_SHORT).show();
            //转到手机设置界面，用户设置GPS
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            //   Intent intent=new Intent(Settings.ACTION_SECURITY_SETTINGS);
            startActivityForResult(intent, 0); //设置完成后返回到原来的界面
            new AlertDialog.Builder(this).setMessage("GPS成功开启").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    LocationManager locationManager = (LocationManager) getApplication().getSystemService(Context.LOCATION_SERVICE);
                    if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
                        Toast.makeText(getApplication(), "GPS没开启成功", Toast.LENGTH_LONG).show();
                    } else {
                        username = sp.getString("username", "").toString();
                        password = sp.getString("password", "").toString();
                        if (username.equals("")) {
                            Intent intent1 = new Intent(getApplication(), MyFragment.class);
                            startActivity(intent1);
                        } else {
                            TelephonyManager telephonyManager = (TelephonyManager) getApplication().getSystemService(Context.TELEPHONY_SERVICE);
                            String imei = telephonyManager.getDeviceId();
                            Service service = new Service();
                            RequestParams params = new RequestParams();
                            params.put("name", username);
                            params.put("password", password);
                            params.put("mac", imei);
                            service.get(getApplication(), "login.php", params, new Listener() {
                                public void Success(String str) {
                                    if (str.equals("1")) {
                                        SharedPreferences.Editor ed = sp.edit();
                                        ed.putString("username", username);
                                        ed.putString("password", password);
                                        ed.commit();
                                        sp.getString("username", "");
                                        Toast.makeText(getApplication(), "登录成功", Toast.LENGTH_SHORT).show();
                                        //判断用户有没有情侣
                                        Service service = new Service();
                                        RequestParams params = new RequestParams();
                                        params.put("name", username);
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
                                                    Intent intent = new Intent(getApplication(), view_mian.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    love_people.name_1 = username;
                                                    love_people.name_2 = "";
                                                    Intent intent = new Intent(getApplication(), view_mian.class);
                                                    startActivity(intent);
                                                    finish();
                                                }


                                                // Toast.makeText(getActivity(), love_people.name_2, Toast.LENGTH_SHORT).show();

                                                //如果有情侣我们就给live—people的全局变量赋值如果没有我们跳转到绑定情侣的界面
                                            }

                                            public void Error() {

                                            }
                                        });
                                        /////////////////////////////////////

                                    } else if (str.equals("0")) {
                                        Log.i("111", str);
                                        Toast.makeText(getApplication(), "您输入的用户名或密码错误", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                public void Error() {

                                }
                            });
                        }
                    }
                }
            }).show();
        } else {
            //弹出Toast
            // Toast.makeText(getApplication(), "GPS is ready", Toast.LENGTH_LONG).show();
            //弹出对话框


            username = sp.getString("username", "").toString();
            password = sp.getString("password", "").toString();
            if (username.equals("")) {
                Handler x = new Handler();
                x.postDelayed(new lunchhandler(), 3000);
            } else {
                TelephonyManager telephonyManager = (TelephonyManager) getApplication().getSystemService(Context.TELEPHONY_SERVICE);
                String imei = telephonyManager.getDeviceId();
                Service service = new Service();
                RequestParams params = new RequestParams();
                params.put("name", username);
                params.put("password", password);
                params.put("mac", imei);
                service.get(getApplication(), "login.php", params, new Listener() {
                    public void Success(String str) {
                        if (str.equals("1")) {
                            SharedPreferences.Editor ed = sp.edit();
                            ed.putString("username", username);
                            ed.putString("password", password);
                            ed.commit();
                            sp.getString("username", "");
                            Toast.makeText(getApplication(), "登录成功", Toast.LENGTH_SHORT).show();
                            //判断用户有没有情侣
                            Service service = new Service();
                            RequestParams params = new RequestParams();
                            params.put("name", username);
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
                                        Intent intent = new Intent(getApplication(), view_mian.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        love_people.name_1 = username;
                                        love_people.name_2 = "";
                                        Intent intent = new Intent(getApplication(), view_mian.class);
                                        startActivity(intent);
                                        finish();
                                    }


                                    // Toast.makeText(getActivity(), love_people.name_2, Toast.LENGTH_SHORT).show();

                                    //如果有情侣我们就给live—people的全局变量赋值如果没有我们跳转到绑定情侣的界面
                                }

                                public void Error() {

                                }
                            });
                            /////////////////////////////////////

                        } else if (str.equals("0")) {
                            Log.i("111", str);
                            Toast.makeText(getApplication(), "您输入的用户名或密码错误", Toast.LENGTH_SHORT).show();
                        } else if (str.equals("-1")) {
                            Toast.makeText(getApplication(), "MAC保存错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    public void Error() {

                    }
                });
            }

        }
    }

}









