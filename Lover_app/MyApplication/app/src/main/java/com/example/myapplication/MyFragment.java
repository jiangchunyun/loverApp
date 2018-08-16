package com.example.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.example.myapplication.personal.view_mian;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 上官刀刀 on 2017/1/14.
 */
public class MyFragment  extends Activity {
    private EditText username;
    private EditText password;
    private Button login;
    private List<user> list1;
    static String name;
    static SharedPreferences sp;
   private TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        sp=getSharedPreferences("lover_app", Context.MODE_PRIVATE);
        username=(EditText)findViewById(R.id.edit1_login);
        password=(EditText)findViewById(R.id.edit2_login);
        login=(Button)findViewById(R.id.button_login);
        login=(Button)findViewById(R.id.button_login);
        register= (TextView) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplication(),MyFragment2.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TelephonyManager telephonyManager=(TelephonyManager) getApplication().getSystemService(Context.TELEPHONY_SERVICE);
                String imei=telephonyManager.getDeviceId();
                Toast.makeText(getApplication(),imei,Toast.LENGTH_LONG).show();
                Service service=new Service();
                RequestParams params=new RequestParams();
                params.put("name",username.getText().toString());
                params.put("password",password.getText().toString());
                params.put("mac",imei);
                service.get(getApplication(), "login.php", params, new Listener() {
                    public void Success(String str) {
                        if(str.equals("1")) {
                             SharedPreferences.Editor ed= sp.edit();
                            ed.putString("username",username.getText().toString());
                            ed.putString("password",password.getText().toString());
                            ed.commit();
                            sp.getString("username","");
                            Toast.makeText(getApplication(), "登录成功", Toast.LENGTH_SHORT).show();
                            name=username.getText().toString();
                            //判断用户有没有情侣
                            Service service=new Service();
                            RequestParams params=new RequestParams();
                            params.put("name",username.getText().toString());
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
                                        Intent intent=new Intent(getApplication(),view_mian.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        love_people.name_1=username.getText().toString();
                                        love_people.name_2="";
                                        Intent intent=new Intent(getApplication(),view_mian.class);
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

                        }
                        else if(str.equals("0"))
                        {
                            Log.i("111",str);
                            Toast.makeText(getApplication(), "您输入的用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                    public void Error() {

                    }
                });
            }
        });
    }




}
