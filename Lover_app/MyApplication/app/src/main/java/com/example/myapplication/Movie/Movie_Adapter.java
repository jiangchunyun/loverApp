package com.example.myapplication.Movie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Listener;
import com.example.myapplication.R;
import com.example.myapplication.Web_view.Web_item;
import com.example.myapplication.personal.imageloader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.R.id.movie_list;

/**
 * Created by MaiBenBen on 2016/10/22.
 */
public class Movie_Adapter extends BaseAdapter{
    private  List<Movie_item> mlist;
    private LayoutInflater minflater;
    private imageloader mimageloader;
    public Movie_Adapter(Context context, List<Movie_item> list)
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
        if(view==null)
        {
           view= minflater.inflate(R.layout.movie_item,null);
        }
        final TextView movie_name= (TextView) view.findViewById(R.id.movie_name);
        TextView movie_star= (TextView) view.findViewById(R.id.movie_star);
        TextView movie_title= (TextView) view.findViewById(R.id.movie_title);
        ImageView movie_image= (ImageView) view.findViewById(R.id.movie_image);
        TextView time= (TextView) view.findViewById(R.id.movie_time);
        TextView url= (TextView) view.findViewById(R.id.url);
        Movie_item movie_item=mlist.get(i);
        Picasso.with(minflater.getContext()).load(movie_item.getUrl()).fit().into(movie_image);
        String string_star="";
        String str=movie_item.getStar();
        for(int j=0;j<str.length();j++)
        {
            if(!(str.charAt(j)==' '||str.charAt(j)=='\n'))
            {
              string_star=string_star+str.charAt(j);
            }
        }
        Log.i("8888",string_star);
        movie_name.setText(movie_item.getName());
        movie_star.setText(string_star);
        time.setText(movie_item.getTime());
        movie_title.setText("电影评分："+movie_item.getTitle());
        url.setText("http://maoyan.com/films/"+movie_item.getMovie_id());
        final View finalView = view;
        return view;
    }
}
