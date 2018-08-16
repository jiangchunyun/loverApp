package com.example.myapplication.personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.MyFragment;
import com.example.myapplication.MyFragment2;
import com.example.myapplication.R;
import com.example.myapplication.about_app.about_our;

/**
 * Created by 上官刀刀 on 2017/3/12.
 */

public class intercalate extends Activity {
    private Button back_user;
    private TextView about_our_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intercalate);
        about_our_text= (TextView) findViewById(R.id.about_our);
        back_user= (Button) findViewById(R.id.back_user);
        about_our_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplication(), about_our.class);
                startActivity(intent);
            }
        });
        back_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),MyFragment.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    }
}
