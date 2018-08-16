package com.example.myapplication.personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Listener;
import com.example.myapplication.MyFragment;
import com.example.myapplication.R;
import com.example.myapplication.Service;
import com.example.myapplication.love_people;
import com.loopj.android.http.RequestParams;

/**
 * Created by 上官刀刀 on 2017/4/9.
 */

public class comment_dialog extends Activity {
  private EditText comment_ed;
    private Button comment_button;
    private String comment_id;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorize_dialog);
        final TextView id=(TextView) findViewById(R.id.id);
        final TextView textView=(TextView) findViewById(R.id.text);
        final TextView textView1=(TextView) findViewById(R.id.text2);
        final TextView textView3=(TextView) findViewById(R.id.text3);
        final TextView date= (TextView) findViewById(R.id.date);
        TextView praise= (TextView) findViewById(R.id.praise);
        TextView comment= (TextView) findViewById(R.id.comment);
        comment_button= (Button) findViewById(R.id.comment_button);
        comment_ed= (EditText) findViewById(R.id.editText_comment);
        Intent intent=getIntent();
        textView.setText(intent.getStringExtra("name_1"));
        textView1.setText(intent.getStringExtra("name_2"));
        id.setText(intent.getStringExtra("things"));
        textView3.setText(intent.getStringExtra("location"));
        date.setText(intent.getStringExtra("time"));
        comment_id=intent.getStringExtra("id");
        comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Service service = new Service();
                RequestParams params = new RequestParams();
                params.put("comment_id",comment_id.toString());
                params.put("comment", comment_ed.getText().toString());
                params.put("comment_name", love_people.name_1);
                service.get(getApplication(), "comment.php", params, new Listener() {
                    public void Success(String str) {
                       if(str.equals("1"))
                       {
                           Toast.makeText(getApplication(),"评论成功",Toast.LENGTH_LONG).show();
                       }
                       else if(str.equals("0"))
                       {
                           Toast.makeText(getApplication(),"评论失败",Toast.LENGTH_LONG).show();
                       }

                    }

                    public void Error() {

                    }
                });
            }
        });

    }
}
