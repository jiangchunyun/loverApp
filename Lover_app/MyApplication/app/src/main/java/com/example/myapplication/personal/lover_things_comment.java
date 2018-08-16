package com.example.myapplication.personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapplication.Listener;
import com.example.myapplication.R;
import com.example.myapplication.Service;
import com.example.myapplication.love_people;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 上官刀刀 on 2017/4/13.
 */

public class lover_things_comment extends Activity {
    private ListView comment_main;
    private List<comment> comment_list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lover_things_comment);
        final TextView id=(TextView) findViewById(R.id.id);
        comment_main= (ListView) findViewById(R.id.listview_comment);
        final TextView textView=(TextView) findViewById(R.id.text);
        final TextView textView1=(TextView) findViewById(R.id.text2);
        final TextView textView3=(TextView) findViewById(R.id.text3);
        final TextView date= (TextView) findViewById(R.id.date);
        TextView praise= (TextView) findViewById(R.id.praise);
        TextView comment= (TextView) findViewById(R.id.comment);
        Intent intent=getIntent();
        textView.setText(intent.getStringExtra("name_1"));
        textView1.setText(intent.getStringExtra("name_2"));
        id.setText(intent.getStringExtra("things"));
        textView3.setText(intent.getStringExtra("location"));
        date.setText(intent.getStringExtra("time"));
        //////////
        Service service = new Service();
        RequestParams params = new RequestParams();
        params.put("comment_id",intent.getStringExtra("id").toString());
        service.get(getApplication(), "showcomment.php", params, new Listener() {
            public void Success(String str) {
                Log.i("6666",str);
                if (str.length()>5) {
                    comment_list = new ArrayList<comment>();
                    Gson gson = new Gson();
                    comment_list = gson.fromJson(str, new TypeToken<List<comment>>() {
                    }.getType());
                    comment_main.setAdapter(new MyAdapter_comment(getApplication(), comment_list));
                }
            }

            public void Error() {

            }
        });
    }
}
