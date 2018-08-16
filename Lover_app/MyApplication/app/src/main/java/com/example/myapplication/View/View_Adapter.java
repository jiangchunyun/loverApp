package com.example.myapplication.View;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.personal.imageloader;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by MaiBenBen on 2016/10/22.
 */
public class View_Adapter extends BaseAdapter{
    private  List<view_item> mlist;
    private LayoutInflater minflater;
    private imageloader mimageloader;
    public View_Adapter(Context context, List<view_item> list)
    {
        mlist=list;
        minflater=LayoutInflater.from(context);
        mimageloader=new imageloader();
    }
    @Override
    public int getCount() {
        return mlist.size();
    }
    @Override
    public Object getItem(int i) {
        return mlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // View view1=minflater.inflate(R.layout.main_doctor_1,null);
       // ImageView imageView=(ImageView) view1.findViewById(R.id.image);
       // TextView textView=(TextView) view1.findViewById(R.id.text);
       // doctor bean=mlist.get(i);
        // imageView.setImageResource(R.drawable.d_a);
        if(view==null)
        {
           view= minflater.inflate(R.layout.view_item,null);
        }
        TextView view_name= (TextView) view.findViewById(R.id.view_name);
        TextView view_price= (TextView) view.findViewById(R.id.view_price);
        TextView view_place= (TextView) view.findViewById(R.id.view_place);
        TextView view_comment= (TextView) view.findViewById(R.id.view_comment);
        ImageView view_img= (ImageView) view.findViewById(R.id.view_image);
        TextView url= (TextView) view.findViewById(R.id.url);
        final view_item view_item=mlist.get(i);
        Picasso.with(minflater.getContext()).load(view_item.getImg()).fit().into(view_img);
        view_name.setText(view_item.getName());
        view_place.setText("地点："+view_item.getPlace());
        String string_price="";
        String str=view_item.getPrice();
        for(int j=0;j<str.length();j++)
        {
            if(!(str.charAt(j)==' '||str.charAt(j)=='\n'))
            {
                string_price=string_price+str.charAt(j);
            }
        }
        view_price.setText(string_price);
        view_comment.setText(view_item.getComment());
        url.setText(view_item.getUrl());
        return view;
    }
}
