package com.example.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.myapplication.personal.MyAdapter_picture;
import com.example.myapplication.personal.ZQImageViewRoundOval;
import com.loopj.android.http.RequestParams;
import com.zzti.fengyongge.imagepicker.PhotoSelectorActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 上官刀刀 on 2017/1/14.
 */
public class MyFragment2 extends Activity implements AMapLocationListener {
    private Button button;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption = null;
    private double x;
    private double y;
    private  RequestParams params;
    private ZQImageViewRoundOval iv_circle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register);
        params = new RequestParams();
        iv_circle = (ZQImageViewRoundOval)findViewById(R.id.cicle);
        mlocationClient = new AMapLocationClient(getApplication());
        mLocationOption = new AMapLocationClientOption();
        mlocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setMockEnable(true);
        mLocationOption.setOnceLocation(true);
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();
        editText1=(EditText) findViewById(R.id.edit1);
        editText2=(EditText) findViewById(R.id.edit2);
        editText3=(EditText)findViewById(R.id.edit3);
        button=(Button) findViewById(R.id.register_button);
        iv_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), PhotoSelectorActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("limit", 1);//number是选择图片的数量
                startActivityForResult(intent, 0);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if( editText1.getText()== null || editText1.getText().length() <= 0)
                {
                    Toast.makeText(getApplication(), "用户名不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(editText2.getText().toString().equals(""))
                {
                    Toast.makeText(getApplication(), "密码不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(!editText2.getText().toString().equals(editText3.getText().toString()))
                {
                    Toast.makeText(getApplication(), "请确定密码是否一样", Toast.LENGTH_SHORT).show();
                }
                else {
                    Service service = new Service();
                    params.put("name", editText1.getText().toString());
                    params.put("password", editText2.getText().toString());
                    params.put("x", x);
                    params.put("y", y);
                    service.get(getApplication(), "register.php", params, new Listener() {
                        public void Success(String str) {
                           // Toast.makeText(getApplication(), str, Toast.LENGTH_SHORT).show();
                            if (str.equals("1")) {
                                Toast.makeText(getApplication(), "亲爱的 你成为了我们app的一个用户了哦...", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplication(),MyFragment.class);
                                startActivity(intent);
                            } else if (str.equals("0")) {
                                Toast.makeText(getApplication(), "您注册的用户名已经存在", Toast.LENGTH_SHORT).show();
                            } else if (str.equals("-1")) {
                                Toast.makeText(getApplication(), "数据库出现了问题", Toast.LENGTH_SHORT).show();
                            }
                        }

                        public void Error() {

                        }
                    });
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (data != null) {
                    List<String> paths = (List<String>) data.getExtras().getSerializable("photos");//path是选择拍照或者图片的地址数组
                    if(paths.size()>=1) {
                        try {
                            params.put("picture",new File(paths.get(0)));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Bitmap bmp = BitmapFactory.decodeFile(paths.get(0));
                        iv_circle.setImageBitmap(bmp);
                    }
                    //处理代码

                }
                break;
            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
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
                String a = amapLocation.getAddress();
                //     city=amapLocation.getCity();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间

                 // Toast.makeText(getActivity(),a, Toast.LENGTH_SHORT).show();
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }

    }
}
