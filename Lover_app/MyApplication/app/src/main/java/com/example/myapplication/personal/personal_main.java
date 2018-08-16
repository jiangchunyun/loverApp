package com.example.myapplication.personal;


import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Listener;
import com.example.myapplication.R;
import com.example.myapplication.Service;
import com.example.myapplication.love_people;
import com.loopj.android.http.RequestParams;

/**
 * Created by 上官刀刀 on 2017/3/1.
 */

public class personal_main extends Fragment {

    private TextView message;
    private TextView personal_space;
    private TextView intercalate;
    private TextView change;
    private TextView remove;
    private TextView lover;
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.personal_main,container,false);
        message= (TextView) view.findViewById(R.id.message);
        personal_space= (TextView) view.findViewById(R.id.personal_space);
        intercalate= (TextView) view.findViewById(R.id.intercalate);
        change= (TextView) view.findViewById(R.id.change);
        remove= (TextView) view.findViewById(R.id.remove);
        lover= (TextView) view.findViewById(R.id.lover);
        lover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(love_people.name_2.equals("")) {
                    Intent intent = new Intent(getActivity(), love_people.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getActivity(),"亲您已经有情侣不可绑定多个哦....",Toast.LENGTH_LONG).show();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(love_people.name_2.equals(""))
                {
                    Toast.makeText(getActivity(),"请在个人中心绑定情侣",Toast.LENGTH_LONG).show();

                }
                else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("确认")
                            .setMessage("如果你解除了情侣关系那么你们将不再是情侣关系")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Service service = new Service();
                                    RequestParams params = new RequestParams();
                                    params.put("name", love_people.name_1);
                                    params.put("lovername", love_people.name_2);
                                    service.get(getActivity(), "part.php", params, new Listener() {
                                        public void Success(String str) {
                                            if (str.equals("1")) {
                                                Toast.makeText(getActivity(), "你和你的情侣已经解除", Toast.LENGTH_SHORT).show();
                                                love_people.name_1=love_people.name_1;
                                                love_people.name_2="";
                                                Intent intent=new Intent(getActivity(),view_mian.class);
                                                startActivity(intent);
                                            } else if (str.equals("0")) {
                                                Toast.makeText(getActivity(), "解除失败", Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                        public void Error() {

                                        }
                                    });

                                }
                            })
                            .setNegativeButton("否", null)
                            .show();
                /*
                Service service = new Service();
                RequestParams params = new RequestParams();
                params.put("name", love_people.name_1);
                params.put("lovername", love_people.name_2);
                service.get(getActivity(), "part.php", params, new Listener() {
                    public void Success(String str) {
                        Toast.makeText(getActivity(), love_people.name_1+ love_people.name_2 , Toast.LENGTH_SHORT).show();
                         Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
                        Log.i("2222",str);
                    }

                    public void Error() {

                    }
                });*/
                }
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),change.class);
                startActivity(intent);
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),message.class);
                startActivity(intent);
            }
        });
        personal_space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),personal_space.class);
                startActivity(intent);
            }
        });
        intercalate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),intercalate.class);
                startActivity(intent);

            }
        });
        return view;
    }
}
