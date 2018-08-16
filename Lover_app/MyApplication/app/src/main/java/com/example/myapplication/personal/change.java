package com.example.myapplication.personal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.myapplication.Listener;
import com.example.myapplication.MyFragment;
import com.example.myapplication.R;
import com.example.myapplication.Service;
import com.example.myapplication.love_people;
import com.loopj.android.http.RequestParams;
import java.lang.reflect.AccessibleObject;

/**
 * Created by 上官刀刀 on 2017/2/27.
 */

public class change  extends Activity {
    private EditText change1;
    private EditText change2;
    private EditText former;
    private Button commit;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change);
        change1= (EditText) findViewById(R.id.change1);
        change2= (EditText) findViewById(R.id.change2);
        commit= (Button) findViewById(R.id.commit);
        former= (EditText) findViewById(R.id.former);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( former.getText()== null || former.getText().length() <= 0)
                {
                    Toast.makeText(getApplication(), "旧密码不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(change1.getText().toString().equals(""))
                {
                    Toast.makeText(getApplication(), "新密码不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(!change2.getText().toString().equals(change1.getText().toString()))
                {
                    Toast.makeText(getApplication(), "请确定新密码是否一样", Toast.LENGTH_SHORT).show();
                }
                else {
                    Service service = new Service();
                    RequestParams params = new RequestParams();
                    params.put("name", love_people.name_1);
                    params.put("password2", change2.getText());
                    params.put("password1", former.getText());
                    service.get(getApplication(), "revisepassword.php", params, new Listener() {
                        public void Success(String str) {
                            if (str.equals("1")) {
                                Toast.makeText(getApplication(), "修改成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplication(), MyFragment.class);
                                startActivity(intent);
                            } else if (str.equals("0")) {
                                Toast.makeText(getApplication(), "修改失败", Toast.LENGTH_SHORT).show();
                            }

                        }

                        public void Error() {

                        }
                    });
                }

            }
        });

    }
}
