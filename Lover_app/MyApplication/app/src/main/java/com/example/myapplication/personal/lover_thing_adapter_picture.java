package com.example.myapplication.personal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 上官刀刀 on 2017/8/4.
 */

public class lover_thing_adapter_picture extends BaseAdapter {
    private List<lover_thing_picture> mlist;
    private LayoutInflater minflater;
    private imageloader mimageloader;
    public lover_thing_adapter_picture(Context context, List<lover_thing_picture> list)
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
            view= minflater.inflate(R.layout.item_gridview_lover_thing,null);
        }
        lover_thing_picture bean=mlist.get(i);
        ImageView imageView=(ImageView) view.findViewById(R.id.id_item_image);
        imageView.setTag(bean.getPicture());
       // mimageloader.showimagebyascynctask(imageView,bean.getPortrait());
        Picasso.with(minflater.getContext()).load(bean.getPicture()).fit().into(imageView);


        return view;
    }
}

