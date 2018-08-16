package com.example.myapplication.personal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by MaiBenBen on 2016/10/22.
 */
public class MyAdapter_picture extends BaseAdapter{
    private  List<String> mlist;
    private LayoutInflater minflater;
    private imageloader mimageloader;
    public MyAdapter_picture(Context context, List<String> list)
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
           view= minflater.inflate(R.layout.item_gridview,null);
        }

        if(i==3)
        { ImageView imageView=(ImageView) view.findViewById(R.id.id_item_image);
            imageView.setImageResource(R.drawable.p_s1);
        }
        else if(i<=2) {
            ImageView imageView=(ImageView) view.findViewById(R.id.id_item_image);
            String bean = mlist.get(i);
            Bitmap bmp = BitmapFactory.decodeFile(bean);
            imageView.setImageBitmap(bmp);
        }
        return view;
    }
}
