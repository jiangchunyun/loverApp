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

/**
 * Created by MaiBenBen on 2016/10/22.
 */
public class MyAdapter_comment extends BaseAdapter{
    private  List<comment> mlist;
    private LayoutInflater minflater;
    private imageloader mimageloader;
    public MyAdapter_comment(Context context, List<comment> list)
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
           view= minflater.inflate(R.layout.comment,null);
        }
        comment comment_1=mlist.get(i);
        TextView comment= (TextView) view.findViewById(R.id.comment_id);
        TextView comment_thing= (TextView) view.findViewById(R.id.comment);
        comment_thing.setText(comment_1.getComment_name());
        comment.setText(":"+comment_1.getComment());
        return view;
    }
}
