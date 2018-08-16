package com.example.myapplication.personal;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.myapplication.Listener;
import com.example.myapplication.R;
import com.example.myapplication.Service;
import com.example.myapplication.love_people;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by MaiBenBen on 2016/10/22.
 */
public class MyAdapter_lover_things extends BaseAdapter  {
    private  List<lover_things> mlist;
    private LayoutInflater minflater;
    private List<comment>   mcomment_list;
    private GeocodeSearch geocoderSearch;
    private ListView mlistView;
    public MyAdapter_lover_things(Context context, List<lover_things> list,ListView listView)
    {
        mlistView=listView;
        mlist=list;
        minflater=LayoutInflater.from(context);
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
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int delete_id=i;
        if(view==null)
        {
           view= minflater.inflate(R.layout.lover_things,null);
        }
        final TextView id=(TextView) view.findViewById(R.id.id);
        final TextView textView=(TextView) view.findViewById(R.id.text);
        final TextView textView1=(TextView) view.findViewById(R.id.text2);
        final TextView textView3=(TextView) view.findViewById(R.id.text3);
        final TextView date= (TextView) view.findViewById(R.id.date);
        final TextView praise= (TextView) view.findViewById(R.id.praise);
        TextView comment= (TextView) view.findViewById(R.id.comment);
        final TextView id_id= (TextView) view.findViewById(R.id.id_id);
        TextView delete= (TextView) view.findViewById(R.id.delete);
        GridView gridView= (GridView) view.findViewById(R.id.id_gradview);
        ZQImageViewRoundOval image_1= (ZQImageViewRoundOval) view.findViewById(R.id.image_1);
        ZQImageViewRoundOval image_2= (ZQImageViewRoundOval) view.findViewById(R.id.image_2);
        View  message=view.findViewById(R.id.message_lover_things);
        ListView mlistview= (ListView) view.findViewById(R.id.listview_comment);
        lover_things bean=mlist.get(i);
        //如果是自己发的说说可以删除不是自己不可以删除
        if(bean.getName().toString().equals(love_people.name_1))
        {
            delete.setVisibility(View.VISIBLE);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(minflater.getContext());
                    builder.setMessage("确定删除说说吗？");
                    builder.setTitle("提示");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                        Service service = new Service();
                        RequestParams params = new RequestParams();
                        params.put("id", id_id.getText());
                    service.get(minflater.getContext(), "deleteLive.php", params, new Listener() {
                        public void Success(String str) {

                        }

                        public void Error() {

                        }
                    });
                        updateDataSet(delete_id);
                    }
                    });
                   builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                       @Override
                         public void onClick(DialogInterface dialog, int which) {
                              dialog.dismiss();
                              }
                         });
                    builder.create().show();


                }
            });
        }
        else
        {
            delete.setVisibility(View.INVISIBLE);
        }
        imageloader mimageloader= new imageloader();
        image_1.setTag(bean.getPortrait().get(0).getPortrait());
        image_2.setTag(bean.getPortrait().get(1).getPortrait());
        Picasso.with(minflater.getContext()).load(bean.getPortrait().get(0).getPortrait()).into(image_1);
        Picasso.with(minflater.getContext()).load(bean.getPortrait().get(1).getPortrait()).into(image_2);
        gridView.setAdapter(new lover_thing_adapter_picture(minflater.getContext(),bean.getPicture()));
        setListViewHeightBasedOnChildren(gridView);
        textView.setText(bean.getName());
        id_id.setText(bean.getId());
        textView1.setText(bean.getLovername());
        id.setText(bean.getThings());
        textView3.setText(bean.getLocation());
        date.setText(bean.getTimedate());
        mlistview.setAdapter(new MyAdapter_comment(minflater.getContext(),bean.getComment()));
        setListViewHeight(mlistview);
        Service service = new Service();
        RequestParams params = new RequestParams();
        params.put("thing_id", id_id.getText());
        params.put("people", love_people.name_1);
        service.get(minflater.getContext(), "check_praise.php", params, new Listener() {
            public void Success(String str) {
                if (str.equals("0"))
                  praise.setBackgroundResource(R.drawable.u_p);
                else if(str.equals("1"))
                    praise.setBackgroundResource(R.drawable.u_p1);

            }

            public void Error() {

            }
        });
        message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(minflater.getContext(),lover_things_comment.class);
                    intent.putExtra("name_1",textView.getText());
                    intent.putExtra("name_2",textView1.getText());
                    intent.putExtra("things",id.getText());
                    intent.putExtra("location",textView3.getText());
                    intent.putExtra("time",date.getText());
                    intent.putExtra("id",id_id.getText());
                    minflater.getContext().startActivity(intent);
                }
            });
        praise.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Log.i("0.00","333333");
                   Service service = new Service();
                   RequestParams params = new RequestParams();
                   params.put("thing_id", id_id.getText());
                   params.put("people", love_people.name_1);
                   service.get(minflater.getContext(), "praise.php", params, new Listener() {
                       public void Success(String str) {
                           if (str.equals("-1")) {
                             praise.setBackgroundResource(R.drawable.u_p1);
                               Toast.makeText(minflater.getContext(), "这个不错", Toast.LENGTH_LONG).show();
                           }
                           else if(str.equals("0"))
                               Toast.makeText(minflater.getContext(),"不可重复点赞",Toast.LENGTH_LONG).show();

                       }

                       public void Error() {

                       }
                   });
               }
           });
       comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(minflater.getContext(),comment_dialog.class);
                intent.putExtra("name_1",textView.getText());
                intent.putExtra("name_2",textView1.getText());
                intent.putExtra("things",id.getText());
                intent.putExtra("location",textView3.getText());
                intent.putExtra("time",date.getText());
                intent.putExtra("id",id_id.getText());
                minflater.getContext().startActivity(intent);
            }
        });
        return view;
    }
    public void updateDataSet(int position) {
        mlist.remove(position);
        notifyDataSetChanged();
    }
    public void notifyDataSetChanged(ListView listView, int position) {
        /**第一个可见的位置**/
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        /**最后一个可见的位置**/
        int lastVisiblePosition = listView.getLastVisiblePosition();

        /**在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            /**获取指定位置view对象**/
            View view = listView.getChildAt(position - firstVisiblePosition);
            getView(position, view, listView);
        }

    }
    public void setListViewHeight(ListView listView) {



        ListAdapter listAdapter = listView.getAdapter();



        if (listAdapter == null) {



            return;



        }



        int totalHeight = 0;


        for (int i = 0; i < listAdapter.getCount(); i++) {



            View listItem = listAdapter.getView(i, null, listView);



            listItem.measure(0, 0);



            totalHeight += listItem.getMeasuredHeight();



        }


        ViewGroup.LayoutParams params = listView.getLayoutParams();



        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));



        listView.setLayoutParams(params);



    }
    public static void setListViewHeightBasedOnChildren(GridView listView) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = 3;// listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        listView.setLayoutParams(params);
    }



}
