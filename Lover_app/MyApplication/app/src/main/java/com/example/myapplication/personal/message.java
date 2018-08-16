package com.example.myapplication.personal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.myapplication.Listener;
import com.example.myapplication.R;
import com.example.myapplication.Service;
import com.example.myapplication.base_x_y;
import com.example.myapplication.love_people;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.zzti.fengyongge.imagepicker.PhotoSelectorActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by 上官刀刀 on 2017/3/12.
 */

public class message extends Activity {
    private List<everyusers> list;
    private TextView name;
    private TextView lovername;
    private TextView id;
    private ZQImageViewRoundOval head_picture;
    private GeocodeSearch geocoderSearch;
    private imageloader mimageloader;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        name= (TextView) findViewById(R.id.name);
        lovername= (TextView) findViewById(R.id.lovername);
        id= (TextView) findViewById(R.id.id);
        head_picture= (ZQImageViewRoundOval) findViewById(R.id.head_picture);
        head_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), PhotoSelectorActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("limit", 1);//number是选择图片的数量
                startActivityForResult(intent, 0);
            }
        });
        mimageloader=new imageloader();
        Service service = new Service();
        RequestParams params = new RequestParams();
        params.put("name", love_people.name_1);
        service.get(getApplication(), "getinformation.php", params, new Listener() {
            public void Success(String str) {
               //  Toast.makeText(getApplication(), str, Toast.LENGTH_SHORT).show();
                Log.i("1111111111111111111111",str);
                list = new ArrayList<everyusers>();
                Gson gson = new Gson();
                everyusers user = gson.fromJson(str,everyusers.class);
                name.setText(user.getName().toString());
                head_picture.setTag(user.getPortrait());
                Picasso.with(getApplication()).load(user.getPortrait()).fit().into(head_picture);
                mimageloader.showimagebyascynctask(head_picture,user.getPortrait());
                if(love_people.name_2.equals(""))
                {
                    lovername.setText("没有情侣");
                }
                else
                lovername.setText(user.getLovername().toString());
                geocoderSearch = new GeocodeSearch(getApplication());
                geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
                    @Override
                    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                       id.setText(regeocodeResult.getRegeocodeAddress().getFormatAddress());
                       // Toast.makeText(getApplication(), regeocodeResult.getRegeocodeAddress().getFormatAddress(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

                    }
                });
                RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(love_people.x1, love_people.y1), 200,GeocodeSearch.AMAP);

                geocoderSearch.getFromLocationAsyn(query);

            }
            public void Error() {

            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (data != null) {
                    List<String> paths = (List<String>) data.getExtras().getSerializable("photos");//path是选择拍照或者图片的地址数组
                    if(paths.size()>=1) {
                        Service service = new Service();
                        RequestParams params = new RequestParams();
                        params.put("name", love_people.name_1);
                        try {
                            params.put("picture",new File(paths.get(0)));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        service.get(getApplication(), "ModifyPortrait.php", params, new Listener() {
                            public void Success(String str) {
                                if(str.equals("1"))
                                {
                                    Toast.makeText(getApplication(), "更换头像成功", Toast.LENGTH_SHORT).show();
                                }

                            }
                            public void Error() {

                            }
                        });

                        Bitmap bmp = BitmapFactory.decodeFile(paths.get(0));
                        head_picture.setImageBitmap(bmp);
                    }
                    //处理代码

                }
                break;
            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
